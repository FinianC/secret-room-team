package com.secret.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("车队类型vo")
public class FleetTypeVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

}
