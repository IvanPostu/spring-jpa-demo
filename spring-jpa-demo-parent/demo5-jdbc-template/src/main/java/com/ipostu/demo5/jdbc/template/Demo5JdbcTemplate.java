package com.ipostu.demo5.jdbc.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo5JdbcTemplate {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local,default,clean");
        SpringApplication.run(Demo5JdbcTemplate.class, args);
    }
}
