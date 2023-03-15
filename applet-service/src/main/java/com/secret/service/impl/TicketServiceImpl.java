package com.secret.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.secret.config.MiniAppBean;
import com.secret.constant.OrderPayConstant;
import com.secret.constant.RS;
import com.secret.entity.MyTicketEntity;
import com.secret.entity.TicketEntity;
import com.secret.entity.TicketPayEntity;
import com.secret.entity.UserEntity;
import com.secret.enums.applet.RedisDelayQueueEnum;
import com.secret.enums.applet.TicketStatusEnum;
import com.secret.exception.ServiceException;
import com.secret.mapper.TicketMapper;
import com.secret.params.applet.TicketQueryParam;
import com.secret.params.applet.purchaseTicketParam;
import com.secret.service.MyTicketService;
import com.secret.service.TicketPayService;
import com.secret.service.TicketService;
import com.secret.service.UserService;
import com.secret.utils.*;
import com.secret.vo.R;
import com.secret.vo.applet.TicketVo;
import com.secret.vo.applet.UserVerificationVo;
import com.secret.vo.applet.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2023-02-26
 */
@Service
@Slf4j
public class TicketServiceImpl extends ServiceImpl<TicketMapper, TicketEntity> implements TicketService {

    @Resource
    private TicketMapper ticketMapper;

    @Resource
    private MyTicketService myTicketService;

    @Resource
    private MiniAppBean miniAppBean;

    @Resource
    private WxPayService wxPayService;

    @Resource
    private TicketPayService ticketPayService;

    @Resource
    private UserService userService;

    @Resource
    private RedisDelayQueueUtil redisDelayQueueUtil;

    @Override
    public Page<TicketVo> page(TicketQueryParam ticketQueryParam) {
        Page<TicketEntity> page = new Page<>(ticketQueryParam.getCurrent(), ticketQueryParam.getPageSize());
        return  ticketMapper.page(page, ticketQueryParam);
    }

    @Override
    public TicketVo detailById(Integer id) {
        return  ticketMapper.detailById(id);
    }

    @Override
    @Transactional
    public R<WxPayMpOrderResult> purchaseTicket(purchaseTicketParam purchaseTicketParam) {
        UserVerificationVo<UserVo> userInfo = UserLoginUtils.getUserInfo();
        UserVo user = userInfo.getUser();
        String lock = user.getId()+""+purchaseTicketParam.getTicketId();
        boolean distributeLock  = true;
        try {
            distributeLock = RedisUtils.getDistributeLock(lock, 30);
            SecretRoomAssert.notNull(distributeLock,RS.DO_NOT_REPEAT_PURCHASE);
            TicketEntity byId = getById(purchaseTicketParam.getTicketId());
            SecretRoomAssert.notNull(byId,RS.TICKET_NOT_FOUND);
            // 重试3次扣库存操作
            for(int i = 0;i < 3 ;i++){
                SecretRoomAssert.isTrue(byId.getStock()>0,RS.NOT_ENOUGH_STOCK);
                boolean update = update(new LambdaUpdateWrapper<TicketEntity>().set(TicketEntity::getStock, byId.getStock() - 1).eq(TicketEntity::getId, byId.getId()).eq(TicketEntity::getUpdateTime, byId.getUpdateTime()));
                if(update){
                    break;
                }
                byId =  getById(purchaseTicketParam.getTicketId());
            }
            MyTicketEntity myTicketEntity = initOrder(byId, user);
            TicketPayEntity ticketPayEntity = initTicketPay(myTicketEntity);
            UserEntity userEntity = userService.getById(user.getId());
            WxPayMpOrderResult pay = pay(userEntity.getOpenId(), ticketPayEntity.getPayNum(), ticketPayEntity.getPayPrice().toString(), byId.getName());
            Map<String, String> param = new HashMap<>();
            param.put(OrderPayConstant.ORDER_ID, myTicketEntity.getId().toString());
            param.put(OrderPayConstant.REMARK, "订单支付超时，自动取消订单");
            param.put(OrderPayConstant.PAY_NO,ticketPayEntity.getPayNum());
            redisDelayQueueUtil.addDelayQueue(param,miniAppBean.getPayTimeOut(), TimeUnit.SECONDS, RedisDelayQueueEnum.ORDER_PAYMENT_TIMEOUT.getCode());
            return R.success(pay);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("purchaseTicket error  {}", e);
        }finally {
            if(distributeLock){
                RedisUtils.unDistributeLock(lock);
            }
        }
        return R.fail(RS.TICKET_NOT_FOUND);
    }

    /**
     * 初始化订单
     * @param ticketEntity
     * @param userVo
     * @return
     */
    MyTicketEntity initOrder(TicketEntity ticketEntity, UserVo userVo){
        MyTicketEntity myTicketEntity = new MyTicketEntity();
        myTicketEntity.setTicketId(ticketEntity.getId());
        myTicketEntity.setPrice(ticketEntity.getPrice());
        myTicketEntity.setOrderNum(StringUtil.createOrderNo("MT", 18));
        myTicketEntity.setStatus(TicketStatusEnum.TO_BE_PAID.getCode());
        myTicketEntity.setUserId(userVo.getId());
        myTicketService.save(myTicketEntity);
        return  myTicketEntity;
    }

    /**
     * 初始化 支付信息
     * @param myTicketEntity
     * @return
     */
    TicketPayEntity initTicketPay(MyTicketEntity myTicketEntity){
        TicketPayEntity ticketPayEntity = new TicketPayEntity();
        ticketPayEntity.setPayNum(StringUtil.createOrderNo( 18));
        ticketPayEntity.setStatus(TicketStatusEnum.TO_BE_PAID.getCode());
        ticketPayEntity.setPayPrice(myTicketEntity.getPrice());
        ticketPayEntity.setOrderId(myTicketEntity.getId());
        ticketPayService.save(ticketPayEntity);
        return  ticketPayEntity;
    }

    public WxPayMpOrderResult pay(String openId,String payNo,String payMoney,String productName){
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        }catch (Exception e){
            log.error("Failed to obtain IP address,pay number {}",payNo,e);
        }
        LocalDateTime now = LocalDateTime.now();
        String nowStr = DateUtil.localDateTimeToStr(now, DateUtil.dfDateTime);
        LocalDateTime localDateTime = now.plusMinutes(10);
        String timeExpire = DateUtil.localDateTimeToStr(localDateTime, DateUtil.dfDateTime);
        final WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                //调起支付的人的 openId
                .openid(openId)
                //订单编号
                .outTradeNo(payNo)
                //小程序支付
                .tradeType(WxPayConstants.TradeType.JSAPI)
                //订单金额(单位是分)BaseWxPayRequest.yuanToFen()这个方法是将元转分
                .totalFee(BaseWxPayRequest.yuanToFen(payMoney))
                //商品描述
                .body(productName)
                //获取本地IP
                .spbillCreateIp(hostAddress)
                //回调的 URL 地址
                .notifyUrl(miniAppBean.getPayNotify())
                //过期时间 格式yyyyMMddHHmmss (一般设置个10分钟后)
                .timeExpire(timeExpire)
                //当前时间 格式yyyyMMddHHmmss
                .timeStart(nowStr)
                .build();
        wxPayUnifiedOrderRequest.setSignType(WxPayConstants.SignType.MD5);
        WxPayMpOrderResult order = null;
        try {
            order = wxPayService.createOrder(wxPayUnifiedOrderRequest);
        } catch (WxPayException e) {
            throw new ServiceException(e.getErrCodeDes());
        }
        return order;
    }


    public void refund(String totalFee,String payNum,String refundNo,String refundFee) {
        WxPayRefundRequest wxPayRefundRequest = WxPayRefundRequest.newBuilder()
                //订单总金额(分)
                .totalFee(BaseWxPayRequest.yuanToFen(totalFee))
                //订单编号
                .outTradeNo(payNum)
                //退款编号
                .outRefundNo(refundNo)
                //退款金额(分)
                .refundFee(BaseWxPayRequest.yuanToFen(refundFee))
                //回调接口
                .notifyUrl(miniAppBean.getRefund())
                .build();
        try {
            wxPayService.refund(wxPayRefundRequest);
        } catch (WxPayException e) {
            throw new ServiceException(RS.REFUND_ERROR);
        }
    }

    @Override
    public String getNameById(Integer id) {
        return  getOne(new LambdaQueryWrapper<TicketEntity>().select(TicketEntity::getName).eq(TicketEntity::getId,id)).getName();
    }

    @Override
    public Boolean increaseInventory(Integer id) {
       return  ticketMapper.increaseInventory(id);
    }
}
