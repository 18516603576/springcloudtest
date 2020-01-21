package com.springcloudtest.eureka.server01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringcloudtestEurekaServer01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudtestEurekaServer01Application.class, args);
	}

}
