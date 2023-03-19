package com.secret.controller;


import com.secret.service.SysConfigService;
import com.secret.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2023-03-19
 */
@RestController
@RequestMapping("/sysConfig")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    @ApiOperation(value = "获取订单超时的时间",notes = "单位 分钟", httpMethod = "GET")
    @GetMapping("/getOrderTimeout")
    public R<Long> getOrderTimeout(){
        return R.success(sysConfigService.getOrderTimeout());
    }

    @ApiOperation(value = "获取二维码过期时间",notes = "单位 分钟", httpMethod = "GET")
    @GetMapping("/getQrCodeTimeout")
    public R<Long> getQrCodeTimeout(){
        return R.success(sysConfigService.getQrCodeTimeout());
    }

}

