package com.secret.model.dto;

import lombok.Data;

@Data
public class WebSocketCommand {

    private String command;

    private String target;
}
