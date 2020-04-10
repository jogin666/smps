package com.zy.smps_password_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableEurekaClient
public class SMPSPasswordServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SMPSPasswordServiceApplication.class, args);
    }

}
