package com.banking.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class swaggerConfig {
	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)          
	      .select()
	      .apis(RequestHandlerSelectors.basePackage("com.banking"))
	      //.apis(RequestHandlerSelectors.basePackage("com.banking.auth.controller"))
	      .build()
	      .apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Banking API Documentation", 
	      "APIs for Banking Application",
	      "1.0",
	      "Free to use",
	      new Contact("Gitesh Jain",null, "giteshjain844@gmail.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
}
