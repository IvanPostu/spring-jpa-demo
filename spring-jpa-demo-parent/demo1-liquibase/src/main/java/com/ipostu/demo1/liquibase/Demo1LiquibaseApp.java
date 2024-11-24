package com.ipostu.demo1.liquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1LiquibaseApp {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local,default");
        SpringApplication.run(Demo1LiquibaseApp.class, args);
    }
}
