package com.secret.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    /**
     * 用户余额账户 MEMBER_BALANCE_ACCOUNT
     */
    GROUP_ONLY(1, "仅组队"),

    RELEASE_GROUP(2, "发布组队");


    private Integer code;
    private String message;


    UserRoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
