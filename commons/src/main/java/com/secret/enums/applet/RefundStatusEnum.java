package com.secret.enums.applet;

import lombok.Getter;

@Getter
public enum RefundStatusEnum {

    SUCCESS(1, "成功"),

    FAIL(0,"失败"),
    WAITING(2,"退款中"),
    ;


    private Integer code;
    private String message;

    RefundStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getRefundStatusEnum(Integer code) {

        for (RefundStatusEnum var : RefundStatusEnum.values()) {
            if (code.equals(var.getCode()) )
                return var.getMessage();
        }
        return "";
    }

}
