package com.zy.spms_server3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ThirdSMPSServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThirdSMPSServerApplication.class, args);
    }

}
