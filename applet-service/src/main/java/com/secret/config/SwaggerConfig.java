package com.secret.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.List;

/**
 * Swagger 配置
 *
 * @author Myles Yang
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {

	@Autowired
	private Environment environment;

	@Bean
	public Docket docket() {
		// boolean isDocEnable = environment.acceptsProfiles(Profiles.of("dev", "test"));
		boolean isDocEnable = true;
		if (isDocEnable) {
			log.debug("Swagger Doc has been disabled.");
		}
		return buildDocket("Default",
				"com.secret.controller",
				"/**")
				.enable(isDocEnable);
	}

	/**
	 * 自定义一个Apikey
	 * 这是一个包含在header中键名为Authorization的JWT标识
	 */
	private ApiKey apiKey() {
		return new ApiKey("token", "token", "header");
	}

	/**
	 * 配置JWT的SecurityContext 并设置全局生效
	 */
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("token", authorizationScopes));
	}

	private Docket buildDocket(String groupName, String basePackage, String antPattern) {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName(groupName)
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.ant(antPattern))
				.build()
				.apiInfo(apiInfo())
				.directModelSubstitute(Temporal.class, String.class);

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Secret Room API Documentation.")
				.description("The API documentation for secret room.")
				.version("1.0.0")
				.contact(new Contact("Finian", "https://github.com/FinianC/secret-room-team", "13923557263@163.com"))
				.license("MIT")
				.licenseUrl("")
				.build();
	}
}
