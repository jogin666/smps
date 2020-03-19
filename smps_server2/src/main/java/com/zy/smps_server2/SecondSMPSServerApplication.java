package com.zy.smps_server2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SecondSMPSServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondSMPSServerApplication.class, args);
    }

}
