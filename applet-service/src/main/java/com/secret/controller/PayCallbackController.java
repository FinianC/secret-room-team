package com.secret.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.secret.entity.MyTicketEntity;
import com.secret.entity.TicketPayEntity;
import com.secret.enums.applet.TicketStatusEnum;
import com.secret.service.MyTicketService;
import com.secret.service.TicketPayService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/callback")
@Slf4j
public class PayCallbackController {

    @Resource
    private WxPayService wxPayService;

    @Resource
    private TicketPayService ticketPayService;

    @Resource
    private MyTicketService myTicketService;

    @ApiOperation(value = "支付回调", notes = "微信系统通知支付成功接口", httpMethod = "GET")
    @RequestMapping("/pay")
    @Transactional
    public String payBack(HttpServletRequest request) {
        String payNum = null;
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlResult);
            //getOutTradeNo 自己生成的支付单号
             payNum = notifyResult.getOutTradeNo();
            if("SUCCESS".equals(notifyResult.getResultCode())) {
                log.info("================>WeChat payment callback ：order number <{}>",payNum);
                TicketPayEntity ticketPayEntity = ticketPayService.getOne(new LambdaQueryWrapper<TicketPayEntity>().eq(TicketPayEntity::getPayNum, payNum));
                if(!TicketStatusEnum.TO_BE_PAID.getCode().equals(ticketPayEntity.getStatus()) ){
                    return WxPayNotifyResponse.success("成功");
                }
                MyTicketEntity myTicketEntity = myTicketService.getOne(new LambdaQueryWrapper<MyTicketEntity>().eq(MyTicketEntity::getId, ticketPayEntity.getOrderId()));
                ticketPayEntity.setStatus(TicketStatusEnum.PAYMENT_SUCCEEDED.getCode());
                myTicketEntity.setStatus(TicketStatusEnum.PAYMENT_SUCCEEDED.getCode());
                myTicketService.updateById(myTicketEntity);
                ticketPayService.updateById(ticketPayEntity);
            }
            return WxPayNotifyResponse.success("成功");
        }catch (Exception e){
            log.error("payNum {} WeChat callback  result is abnormal,Abnormal cause{}",payNum, e.getMessage());
            return WxPayNotifyResponse.success("code:"+9999+"微信回调结果异常,异常原因:"+e.getMessage());
        }
    }

}
