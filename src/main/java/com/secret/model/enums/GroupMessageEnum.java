package com.secret.model.enums;

import lombok.Getter;

@Getter
public enum GroupMessageEnum {

    OLD("OLD", "旧消息"),

    NEW("NEW","新消息"),;


    private String code;
    private String message;

    GroupMessageEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {

        for (GroupMessageEnum var : GroupMessageEnum.values()) {
            if (code.equals(var.getCode()) )
                return var.getMessage();
        }
        return "";
    }
}
