package com.secret.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxAccessTokenVO implements Serializable {

    /** 获取到的凭证.*/
    private String accessToken;

    /** 凭证有效时间，单位：秒.*/
    private Integer expiresIn;

}
