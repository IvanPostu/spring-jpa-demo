package com.ipostu.demo9.pagination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo9Pagination {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local,default");
        SpringApplication.run(Demo9Pagination.class, args);
    }
}
