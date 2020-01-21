package com.springcloudtest.eureka.server02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringcloudtestEurekaServer02Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudtestEurekaServer02Application.class, args);
	}

}
