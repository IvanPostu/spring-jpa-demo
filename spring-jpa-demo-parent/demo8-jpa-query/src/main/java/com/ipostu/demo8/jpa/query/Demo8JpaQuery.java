package com.ipostu.demo8.jpa.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo8JpaQuery {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local,default,clean");
        SpringApplication.run(Demo8JpaQuery.class, args);
    }
}
