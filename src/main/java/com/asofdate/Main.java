package com.asofdate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(basePackages = {"com.asofdate.dao.impl","com.asofdate.service.impl", "com.asofdate.controller", "com.asofdate.authentication"})
@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class Main {
    // main函数，Spring Boot程序入口
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}