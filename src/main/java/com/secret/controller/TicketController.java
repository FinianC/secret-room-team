package com.secret.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.ThemeEntity;
import com.secret.model.params.TicketQueryParam;
import com.secret.model.vo.R;
import com.secret.model.vo.ThemeVo;
import com.secret.model.vo.TicketVo;
import com.secret.service.TicketService;
import com.secret.utils.TransferUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


    @Autowired
    private TicketService  ticketService;


    @ApiOperation(value = "密室分页列表", httpMethod = "POST")
    @GetMapping("/page")
    public R<Page<TicketVo>> page(@RequestBody TicketQueryParam ticketQueryParam){
        Page<TicketVo> page = ticketService.page(ticketQueryParam);
        return R.success(page);
    }
}

