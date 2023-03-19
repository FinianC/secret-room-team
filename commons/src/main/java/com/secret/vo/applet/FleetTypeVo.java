package com.secret.vo.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("车队类型vo")
public class FleetTypeVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

}
