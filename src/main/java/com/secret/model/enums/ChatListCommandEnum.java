package com.secret.model.enums;

import lombok.Getter;

@Getter
public enum ChatListCommandEnum {


    GET_ALL_CHAT_LIST("allChatList", "获取所有聊天列表");


    private String code;
    private String message;


    ChatListCommandEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
