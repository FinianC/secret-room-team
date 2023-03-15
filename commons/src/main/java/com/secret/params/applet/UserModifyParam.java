package com.secret.params.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("用户修改参数")
public class UserModifyParam {

    @ApiModelProperty(value = "id")
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "真实姓名")
    private String trueName;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String headerImg;

    @ApiModelProperty(value = "微信号")
    private String wechatNumber;

    @ApiModelProperty(value = "性别 0保密  1女 2男")
    private Integer sex;

}
