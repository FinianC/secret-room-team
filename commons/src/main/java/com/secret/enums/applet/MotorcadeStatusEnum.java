package com.secret.enums.applet;

import lombok.Getter;

/**
 * @program: secret-room
 * @description: 车队状态
 * @author: 陈迪
 * @create: 2023-01-21 15:26
 **/
@Getter
public enum MotorcadeStatusEnum {

    HAVE_IN_HAND(1, "组队中"),

    SUCCESS(2,"组队成功"),

    FAIL(3,"组队失败");


    private Integer code;
    private String message;

    MotorcadeStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(Integer code) {

        for (MotorcadeStatusEnum var : MotorcadeStatusEnum.values()) {
            if (code.equals(var.getCode()) )
                return var.getMessage();
        }
        return "";
    }
}
