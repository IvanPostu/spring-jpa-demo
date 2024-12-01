package com.ipostu.demo12.legacy.db.mapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo12LegacyDbMapping {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local");
        SpringApplication.run(Demo12LegacyDbMapping.class, args);
    }

}