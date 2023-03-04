package com.secret.model.enums;

import lombok.Getter;

/**
 * 票状态枚举
 */
@Getter
public enum TicketStatusEnum {

    PAYMENT_CLOSE(0, "支付关闭"),

    TO_BE_PAID(1,"等待支付"),

    REFUNDED(2,"已退款"),

    PAYMENT_SUCCEEDED(3,"支付成功/待消费"),

    CONSUMED(4,"已消费");


    private Integer code;
    private String message;

    TicketStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getTicketStatusEnum(Integer code) {

        for (TicketStatusEnum var : TicketStatusEnum.values()) {
            if (code.equals(var.getCode()) )
                return var.getMessage();
        }
        return "";
    }
}
