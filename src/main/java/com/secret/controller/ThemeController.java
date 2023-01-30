package com.secret.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.constant.RS;
import com.secret.model.entity.ThemeEntity;
import com.secret.model.params.GroupMsgContentQueryParam;
import com.secret.model.vo.GroupMsgContentVo;
import com.secret.model.vo.R;
import com.secret.model.vo.ThemeVo;
import com.secret.model.vo.UserVo;
import com.secret.service.ThemeService;
import com.secret.utils.TransferUtils;
import com.secret.utils.UserLoginUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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

