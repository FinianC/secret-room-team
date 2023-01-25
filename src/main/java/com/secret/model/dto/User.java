package com.secret.model.dto;

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
}
