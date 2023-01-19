package com.secret.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @program: secret-room
 * @description: 文件配置
 * @author: 陈迪
 * @create: 2023/01/19 15:48
 */
@Configuration
@Data
public class FileConfig {

    @Value("${file.upload.path}")
    private  String fileUrl;

    @Value("${spring.redirect_uri}")
    private  String baseUrl;
}
