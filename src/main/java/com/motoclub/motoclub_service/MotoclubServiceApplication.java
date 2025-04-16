package com.motoclub.motoclub_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MotoclubServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotoclubServiceApplication.class, args);
	}

}
