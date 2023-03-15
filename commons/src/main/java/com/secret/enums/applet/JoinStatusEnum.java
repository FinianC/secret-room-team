package com.secret.enums.applet;

import lombok.Getter;

@Getter
public enum JoinStatusEnum {

    JOINED(1, "已加入"),

    NOT_JOINED(0,"未加入"),;


    private Integer code;
    private String message;

    JoinStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getJoinStatusEnum(String code) {

        for (GroupMessageEnum var : GroupMessageEnum.values()) {
            if (code.equals(var.getCode()) )
                return var.getMessage();
        }
        return "";
    }
}
