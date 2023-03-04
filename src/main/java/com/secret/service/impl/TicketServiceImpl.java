package com.secret.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.secret.config.MiniAppBean;
import com.secret.constant.RS;
import com.secret.exception.ServiceException;
import com.secret.model.entity.MyTicketEntity;
import com.secret.model.entity.TicketEntity;
import com.secret.mapper.TicketMapper;
import com.secret.model.entity.TicketPayEntity;
import com.secret.model.entity.UserEntity;
import com.secret.model.enums.TicketStatusEnum;
import com.secret.model.params.TicketQueryParam;
import com.secret.model.params.purchaseTicketParam;
import com.secret.model.vo.R;
import com.secret.model.vo.TicketVo;
import com.secret.model.vo.UserVerificationVo;
import com.secret.model.vo.UserVo;
import com.secret.service.MyTicketService;
import com.secret.service.TicketPayService;
import com.secret.service.TicketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.service.UserService;
import com.secret.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

import java.net.InetAddress;
import java.time.LocalDateTime;

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
    public R<WxPayAppOrderResult> purchaseTicket(purchaseTicketParam purchaseTicketParam) {
        UserVerificationVo<UserVo> userInfo = UserLoginUtils.getUserInfo();
        UserVo user = userInfo.getUser();
        String lock = user.getId()+""+purchaseTicketParam.getTicketId();
        boolean distributeLock  = true;
        try {
            distributeLock = RedisUtils.getDistributeLock(lock, 30);
            SecretRoomAssert.notNull(distributeLock,RS.DO_NOT_REPEAT_PURCHASE);
            TicketEntity byId = getById(purchaseTicketParam.getTicketId());
            SecretRoomAssert.notNull(byId,RS.TICKET_NOT_FOUND);
            MyTicketEntity myTicketEntity = initOrder(byId, user);
            TicketPayEntity ticketPayEntity = initTicketPay(myTicketEntity);
            UserEntity userEntity = userService.getById(user.getId());
           return R.success(pay(userEntity.getOpenId(),ticketPayEntity.getPayNum(),ticketPayEntity.getPayPrice().toString(),byId.getName()));
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
        myTicketEntity.setOrderNum(StringUtil.createOrderNo("MT", 16));
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

    public WxPayAppOrderResult pay(String openId,String payNo,String payMoney,String productName){
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        }catch (Exception e){
            log.error("Failed to obtain IP address,pay number {}",payNo,e);
        }
        LocalDateTime now = LocalDateTime.now();
        String nowStr = TimeUtil.localDateTimeToStr(now, TimeUtil.dfDateTime);
        LocalDateTime localDateTime = now.plusMinutes(10);
        String timeExpire = TimeUtil.localDateTimeToStr(localDateTime, TimeUtil.dfDateTime);
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
        WxPayAppOrderResult order = null;
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
}
