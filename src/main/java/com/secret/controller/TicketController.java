package com.secret.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.params.TicketQueryParam;
import com.secret.model.vo.R;
import com.secret.model.vo.TicketVo;
import com.secret.service.TicketService;
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
}

