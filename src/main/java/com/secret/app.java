package com.secret;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application Entrance
 *
 * @author Myles Yang
 * @date 2021-05-01
 */
@EnableScheduling
////@ConfigurationPropertiesScan("com.secret.config.properties")
@SpringBootApplication

//@MapperScan("com.gitee.sunchenbin.mybatis.actable.dao.*")  //固定的
//"com.gitee.sunchenbin.mybatis.actable.manager.*  必须要有
//@SpringBootApplication(scanBasePackages = {"com.secret.*", "com.gitee.sunchenbin.mybatis.actable.manager.*"})
public class app {

	public static void main(String[] args) {
		SpringApplication.run(app.class, args);
	}

}
