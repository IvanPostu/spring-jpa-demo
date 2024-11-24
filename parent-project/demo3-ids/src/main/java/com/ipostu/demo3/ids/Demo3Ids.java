package com.ipostu.demo3.ids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo3Ids {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local,default,clean");
        SpringApplication.run(Demo3Ids.class, args);
    }
}
