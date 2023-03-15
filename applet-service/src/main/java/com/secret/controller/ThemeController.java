package com.secret.controller;


import com.secret.entity.ThemeEntity;
import com.secret.service.ThemeService;
import com.secret.utils.TransferUtils;
import com.secret.vo.R;
import com.secret.vo.applet.ThemeVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 主题 前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@RestController
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @ApiOperation(value = "主题列表", httpMethod = "GET")
    @GetMapping("/list")
    public R<List<ThemeVo>> list(){
        List<ThemeEntity> list = themeService.list();
        List<ThemeVo> themeVos = TransferUtils.transferList(list, ThemeVo.class);
        return R.success(themeVos);
    }

    @ApiOperation(value = "已存在主题列表", httpMethod = "GET")
    @GetMapping("/existsList")
    public R<List<ThemeVo>> existsList(){
        List<ThemeVo> themeVos = themeService.existsList();
        return R.success(themeVos);
    }

}

