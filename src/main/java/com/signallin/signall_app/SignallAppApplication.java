package com.signallin.signall_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients
public class SignallAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SignallAppApplication.class, args);
	}

}
