package com.secret.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.secret.model.params.RefundParam;
import com.secret.model.params.ToPayParam;
import com.secret.model.vo.*;
import com.secret.service.MyTicketService;
import com.secret.utils.UserLoginUtils;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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

    @ApiOperation(value = "分页查询我的密室票/我的订单", httpMethod = "POST")
    @PostMapping("/page")
    public R<Page<MyTicketVo>> page(@RequestBody MyTicketQueryVo myTicketQueryVo) {
        return R.success( myTicketService.page(myTicketQueryVo));
    }

    @ApiOperation(value = "获取消费二维码",notes = "有效期三分钟", httpMethod = "GET")
    @GetMapping("/getQRCode/{orderId}")
    public void getQRCode(@PathVariable Integer orderId, HttpServletResponse response ) {
        myTicketService.getQRCode(orderId,response);
    }
}

