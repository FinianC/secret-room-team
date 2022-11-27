package com.secret.model.enums;

import lombok.Getter;

@Getter
public enum FleetChangesEnum {

    JOIN(1,"", "密逃玩家: {0} ,加入了{1}车队"),

    LEAVE(2,"" ,"密逃玩家: {0} ,离开了{1}车队"),

    SUCCESS(3,"","{1} 车队拼车成功!");


    private Integer code;
    private String handle;
    private String message;

    FleetChangesEnum(Integer code, String handle, String message) {
        this.code = code;
        this.handle = handle;
        this.message = message;
    }

    public static String getHandle(int code) {

        for (FleetChangesEnum var : FleetChangesEnum.values()) {
            if (code == var.getCode())
                return var.getHandle();
        }
        return "";
    }
    public static String getMessage(int code) {

        for (FleetChangesEnum var : FleetChangesEnum.values()) {
            if (code == var.getCode())
                return var.getMessage();
        }
        return "";
    }
}
