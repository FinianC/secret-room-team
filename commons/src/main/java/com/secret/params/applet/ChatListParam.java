package com.secret.params.applet;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("获取聊天列表")
@Data
public class ChatListParam {

    private String command;

}
