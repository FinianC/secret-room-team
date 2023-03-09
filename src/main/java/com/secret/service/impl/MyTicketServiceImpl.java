package com.secret.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.secret.constant.RS;
import com.secret.model.entity.MyTicketEntity;
import com.secret.mapper.MyTicketMapper;
import com.secret.model.entity.RefundRecordEntity;
import com.secret.model.entity.TicketPayEntity;
import com.secret.model.entity.UserEntity;
import com.secret.model.enums.RefundStatusEnum;
import com.secret.model.params.RefundParam;
import com.secret.model.params.ToPayParam;
import com.secret.model.vo.R;
import com.secret.model.vo.UserVo;
import com.secret.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.utils.RedisUtils;
import com.secret.utils.SecretRoomAssert;
import com.secret.utils.StringUtil;
import com.secret.utils.UserLoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public R<WxPayMpOrderResult> toPay(ToPayParam toPayParam) {

        UserVo user =(UserVo) UserLoginUtils.getUserInfo().getUser();
        String lock = user + "" + toPayParam.getOrderId();
        boolean distributeLock = true;
        TicketPayEntity one = null;
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
            log.error("Payment failed no. {} ,error {}",one.getPayNum() , e);
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
}
