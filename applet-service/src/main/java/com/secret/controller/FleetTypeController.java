package com.secret.controller;


import com.secret.entity.FleetTypeEntity;
import com.secret.service.FleetTypeService;
import com.secret.utils.TransferUtils;
import com.secret.vo.R;
import com.secret.vo.applet.FleetTypeVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 车队类型 前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@RestController
@RequestMapping("/fleetType")
public class FleetTypeController {

    @Autowired
    private FleetTypeService fleetTypeService;

    @ApiOperation("车队类型")
    @GetMapping("/list")
    public R<List<FleetTypeVo>> list(){
        List<FleetTypeEntity> list = fleetTypeService.list();
        List<FleetTypeVo> fleetTypeVos = TransferUtils.transferList(list, FleetTypeVo.class);
        return R.success(fleetTypeVos);
    }

}

