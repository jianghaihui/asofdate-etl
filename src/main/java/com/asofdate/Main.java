package com.asofdate;

import com.asofdate.dispatch.InitBatch;
import com.asofdate.utils.Adaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(basePackages = {"com.asofdate.utils", "com.asofdate.platform.dao.impl", "com.asofdate.platform.service.impl", "com.asofdate.platform.controller", "com.asofdate.platform.authentication", "com.asofdate.dispatch", "com.asofdate.dispatch.model"})
@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class Main {
    // main函数，Spring Boot程序入口
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        Adaptor.initDb();
        InitBatch.initBatchInfo();
    }
}