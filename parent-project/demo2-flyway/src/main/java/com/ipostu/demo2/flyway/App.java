package com.ipostu.demo2.flyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local,default");
        SpringApplication.run(App.class, args);
    }
}
