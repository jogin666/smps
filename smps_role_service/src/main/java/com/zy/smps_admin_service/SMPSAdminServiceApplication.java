package com.zy.smps_admin_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.zy.smps_admin_service.mapper"})
public class SMPSAdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SMPSAdminServiceApplication.class, args);
    }

}
