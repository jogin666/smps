package com.zy.smps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class SMPSZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SMPSZuulApplication.class, args);
    }

}
