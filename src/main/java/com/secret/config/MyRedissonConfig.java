package com.secret.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;


    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.dataBase:0}")
    private Integer dataBase;



    /**
     * 所有对Redisson的使用都是通过RedissonClient对象
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+host+":"+port+"").setPassword(password).setDatabase(dataBase);
        return Redisson.create(config);
    }



}
