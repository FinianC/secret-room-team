package com.secret;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application Entrance
 *
 * @author Myles Yang
 * @date 2021-05-01
 */
@EnableScheduling
@SpringBootApplication
public class AppletApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppletApplication.class, args);
	}

}
