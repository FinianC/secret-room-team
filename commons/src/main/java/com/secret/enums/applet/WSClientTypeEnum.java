package com.secret.enums.applet;

import lombok.Getter;

/**
 * @program: secret-room
 * @description: ws连接类型
 * @author: 陈迪
 * @create: 2023-01-25 16:24
 **/
@Getter
public enum WSClientTypeEnum {




    CHAT_LIST("CL", "聊天框列表"),

    CHAT("C", "聊天框");


    private String code;
    private String message;


    WSClientTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
