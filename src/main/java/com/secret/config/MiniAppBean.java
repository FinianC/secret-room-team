package com.secret.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MiniAppBean {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    /** 消息模板ID.*/
    @Value("${wechat.successTemplateId}")
    private String successTemplateId;
    /** 消息模板ID.*/
    @Value("${wechat.cancelTemplateId}")
    private String cancelTemplateId;

    /** 消息模板ID.*/
    @Value("${wechat.failTemplateId}")
    private String failTemplateId;


}
