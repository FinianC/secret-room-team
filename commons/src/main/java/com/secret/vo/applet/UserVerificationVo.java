package com.secret.vo.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="登入返回对象", description="")
public class UserVerificationVo<T> {

    @ApiModelProperty(value = "token凭证")
    private String token;

    @ApiModelProperty(value = "用户信息")
    private T user;

//    @ApiModelProperty(value = "角色权限")
//    List<RoleVo> roles;

//    @ApiModelProperty(value = "菜单数据")
//    private List<MenuVo> menus;

    @ApiModelProperty(value = "是否需要校验权限")
    private Boolean isAuthentication = false;
}
