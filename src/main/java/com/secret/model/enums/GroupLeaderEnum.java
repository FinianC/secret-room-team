package com.secret.model.enums;

import lombok.Getter;

/**
 * @program: secret-room
 * @description: 群主枚举
 * @author: 陈迪
 * @create: 2023-01-19 14:16
 **/
@Getter
public enum GroupLeaderEnum {

    IS(1, "是"),

    NO(0,"不是"),;


    private Integer code;
    private String message;

    GroupLeaderEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getGroupLeaderEnum(Integer code) {

        for (GroupLeaderEnum var : GroupLeaderEnum.values()) {
            if (code.equals(var.getCode()) )
                return var.getMessage();
        }
        return "";
    }
}
