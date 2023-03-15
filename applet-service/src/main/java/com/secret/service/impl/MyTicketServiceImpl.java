package com.secret.service.impl;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.secret.constant.RS;
import com.secret.constant.SecretRoomConstant;
import com.secret.entity.MyTicketEntity;
import com.secret.entity.RefundRecordEntity;
import com.secret.entity.TicketPayEntity;
import com.secret.enums.applet.RefundStatusEnum;
import com.secret.enums.applet.TicketStatusEnum;
import com.secret.mapper.MyTicketMapper;
import com.secret.params.applet.RefundParam;
import com.secret.params.applet.ToPayParam;
import com.secret.service.*;
import com.secret.utils.*;
import com.secret.vo.R;
import com.secret.vo.applet.MyTicketQueryVo;
import com.secret.vo.applet.MyTicketVo;
import com.secret.vo.applet.QrCodeVo;
import com.secret.vo.applet.UserVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2023-03-04
 */
@Service
@Slf4j
public class MyTicketServiceImpl extends ServiceImpl<MyTicketMapper, MyTicketEntity> implements MyTicketService {


    @Resource
    private TicketService ticketService;

    @Resource
    private TicketPayService ticketPayService;

    @Resource
    private UserService userService;

    @Resource
    private MyTicketService myTicketService;

    @Resource
    private RefundRecordService refundRecordService;

    @Resource
    private MyTicketMapper myTicketMapper;

    @Override
    public R<WxPayMpOrderResult> toPay(ToPayParam toPayParam) {

        UserVo user =(UserVo) UserLoginUtils.getUserInfo().getUser();
        String lock = user + "" + toPayParam.getOrderId();
        boolean distributeLock = true;
        TicketPayEntity one ;
        try {
            distributeLock = RedisUtils.getDistributeLock(lock, 30);
            SecretRoomAssert.notNull(distributeLock, RS.DO_NOT_PAY_REPEATEDLY);
            MyTicketEntity myTicketEntity = getById(toPayParam.getOrderId());
            SecretRoomAssert.notNull(myTicketEntity,RS.ORDER_ERROR);
            one = ticketPayService.getOne(new LambdaQueryWrapper<TicketPayEntity>().eq(TicketPayEntity::getOrderId, myTicketEntity.getId()));
            ticketPayService.removeById(one.getId());
            one.setId(null);
            one.setPayNum(StringUtil.createOrderNo(18));
            ticketPayService.save(one);
            String openIdById = userService.getOpenIdById(user.getId());
            WxPayMpOrderResult pay = ticketService.pay(openIdById, one.getPayNum(), one.getPayPrice().toString(), ticketService.getNameById(myTicketEntity.getTicketId()));
            return R.success(pay);
        }catch (Exception e){
            log.error("Payment failed order id {} ,error {}",toPayParam.getOrderId() , e);
            e.printStackTrace();
        }finally {
            if(distributeLock){
                RedisUtils.unDistributeLock(lock);
            }
        }
        return R.fail(RS.PAYMENT_FAILED);
    }

    @Override
    public R<Boolean> refund(RefundParam refundParam) {
        UserLoginUtils.getUserInfo();
        LambdaQueryWrapper<MyTicketEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(refundParam.getOrderId()!=null ,MyTicketEntity::getId,refundParam.getOrderId())
                .or(refundParam.getOrderId()!=null && StringUtil.isNotEmpty(refundParam.getOrderNum()))
                .eq(StringUtil.isNotEmpty(refundParam.getOrderNum()),MyTicketEntity::getOrderNum,refundParam.getOrderNum());
        MyTicketEntity myTicketEntity = myTicketService.getOne(wrapper);
        SecretRoomAssert.notNull(myTicketEntity , RS.ORDER_NOT_FOUND);
        SecretRoomAssert.isTrue(TicketStatusEnum.PAYMENT_SUCCEEDED.getCode().equals(myTicketEntity.getStatus()), RS.REFUND_ERROR);
        TicketPayEntity ticketPayEntity = ticketPayService.getOne(new LambdaQueryWrapper<TicketPayEntity>().eq(TicketPayEntity::getOrderId, myTicketEntity.getId()));
        RefundRecordEntity refundRecordEntity= refundRecordService.getOne(new LambdaQueryWrapper<RefundRecordEntity>().eq(RefundRecordEntity::getOrderId, myTicketEntity.getId()));
        if(refundRecordEntity == null){
            refundRecordEntity = new RefundRecordEntity();
            String refundNo = StringUtil.createOrderNo(18);
            refundRecordEntity.setRefundDesc(refundParam.getRefundDesc());
            refundRecordEntity.setOutRefundNo(refundNo);
            refundRecordEntity.setTotalFee(ticketPayEntity.getPayPrice());
            refundRecordEntity.setRefundFee(ticketPayEntity.getPayPrice());
            refundRecordEntity.setStatus(RefundStatusEnum.WAITING.getCode());
            refundRecordEntity.setPayId(ticketPayEntity.getId());
            refundRecordEntity.setOrderId(myTicketEntity.getId());
            refundRecordService.save(refundRecordEntity);
        }
        ticketService.refund(refundRecordEntity.getTotalFee().toString(),ticketPayEntity.getPayNum(),refundRecordEntity.getOutRefundNo(),refundRecordEntity.getRefundFee().toString());
        return R.success(Boolean.TRUE);
    }

    /**
     * 分页查询我的密室票
     *
     * @param myTicketQueryVo
     * @return
     */
    @Override
    public Page<MyTicketVo> page(MyTicketQueryVo myTicketQueryVo) {
        Page<MyTicketVo> page = new Page<>(myTicketQueryVo.getCurrent(),myTicketQueryVo.getPageSize());
        UserVo user = UserLoginUtils.<UserVo>getUserInfo().getUser();
        return myTicketMapper.page(page, myTicketQueryVo, user.getId());
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @Override
    public MyTicketVo detail(Integer id) {
       UserVo user = UserLoginUtils.<UserVo>getUserInfo().getUser();
       return myTicketMapper.detail(id,user.getId());
    }

    /**
     * 获取消费二维码
     *
     * @param orderId
     * @param response
     */
    @Override
    @SneakyThrows
    public void getQRCode(Integer orderId, HttpServletResponse response) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        QrCodeVo qrCodeVo = new QrCodeVo();
        qrCodeVo.setOrderId(orderId);
        qrCodeVo.setSequence(TextUtils.uuid("",25));
        String content = JSON.toJSONString(qrCodeVo);
        // 设置有效期
        RedisUtils.set(qrCodeVo.getKey(),qrCodeVo.getSequence(), SecretRoomConstant.QR_CODE_EXPIRED);
        String encode = Base64Encoder.encode(content.getBytes());
        QrCodeUtil.generate(encode, 300, 300, "jpg", response.getOutputStream());
    }

}
