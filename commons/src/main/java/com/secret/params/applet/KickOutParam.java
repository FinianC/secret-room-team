package com.secret.params.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: secret-room
 * @description: 踢出车队参数
 * @author: 陈迪
 * @create: 2023/01/20 17:28
 */
@ApiModel("踢出车队参数")
@Data
public class KickOutParam {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "车队id")
    private Integer motorcadeId;
}
