package com.secret.controller;



import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.secret.model.params.RefundParam;
import com.secret.model.params.ToPayParam;

import com.secret.model.vo.R;
import com.secret.service.MyTicketService;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/myTicket")
public class MyTicketController {


    @Resource
    private MyTicketService myTicketService;


    @ApiOperation(value = "去支付", httpMethod = "POST")
    @PostMapping("/toPay")
    public R<WxPayMpOrderResult> toPay(@RequestBody ToPayParam toPayParam) {
        return myTicketService.toPay(toPayParam);
    }

    @ApiOperation(value = "退款", httpMethod = "POST")
    @PostMapping("/refund")
    @Transactional
    public R<Boolean> refund(@RequestBody RefundParam refundParam) {
        return myTicketService.refund(refundParam);
    }
}

