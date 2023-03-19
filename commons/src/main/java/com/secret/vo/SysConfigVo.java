package com.secret.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("系统配置vo")
public class SysConfigVo implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "变量 key")
    private String variable;

    @ApiModelProperty(value = "值")
    private String value;
}
