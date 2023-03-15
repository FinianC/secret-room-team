package com.secret.dto.applet;

import lombok.Data;

import java.security.Principal;

@Data

public class User implements Principal {

    private Integer id ;

    private String username;

    private String password;

    private String role;

    private String type;

    @Override
    public String getName() {
        return username;
    }

    public User() {
    }

    public User(String username, String type) {
        this.username = username;
        this.type = type;
    }
}
