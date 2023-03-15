package com.secret.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.secret.params.applet.TicketQueryParam;
import com.secret.params.applet.purchaseTicketParam;
import com.secret.service.TicketService;
import com.secret.vo.R;
import com.secret.vo.applet.TicketVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2023-02-26
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {


    @Resource
    private TicketService  ticketService;


    @ApiOperation(value = "密室分页列表", httpMethod = "POST")
    @PostMapping("/page")
    public R<Page<TicketVo>> page(@RequestBody TicketQueryParam ticketQueryParam){
        Page<TicketVo> page = ticketService.page(ticketQueryParam);
        return R.success(page);
    }

    @ApiOperation(value = "密室详情", httpMethod = "GET")
    @GetMapping("/detail/{id}")
    public R<TicketVo> detailById(@PathVariable Integer id){
        TicketVo ticketVo = ticketService.detailById(id);
        return R.success(ticketVo);
    }

    @ApiOperation(value = "购买密室票", httpMethod = "POST")
    @PostMapping("/purchaseTicket")
    public R<WxPayMpOrderResult> purchaseTicket(@RequestBody purchaseTicketParam purchaseTicketParam){
        return  ticketService.purchaseTicket(purchaseTicketParam);
    }
}

