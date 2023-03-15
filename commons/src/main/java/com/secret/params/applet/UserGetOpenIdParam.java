package com.secret.params.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("获取用户openid参数")
@Data
public class UserGetOpenIdParam {

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 手机号码;
     */
    @ApiModelProperty("手机号码")
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String headerImg;

    /**
     * 微信Code
     */
    @ApiModelProperty("微信code")
    private String code;


}
