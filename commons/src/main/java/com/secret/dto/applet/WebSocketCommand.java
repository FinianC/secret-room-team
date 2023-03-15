package com.secret.dto.applet;

import lombok.Data;

@Data
public class WebSocketCommand {

    private String command;

    private String target;
}
