package com.secret.model.enums;

import lombok.Getter;

/**
 * @program: secret-room
 * @description: 未读信息枚举
 * @author: 陈迪
 * @create: 2023-01-17 16:09
 **/
@Getter
public enum UnreadMessageEnum {

    EMPTY(0, "无"),

    HAVE(1,"有"),;


    private Integer code;
    private String message;

    UnreadMessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getUnreadMessageEnum(Integer code) {

        for (UnreadMessageEnum var : UnreadMessageEnum.values()) {
            if (code.equals(var.getCode()) )
                return var.getMessage();
        }
        return "";
    }
}
