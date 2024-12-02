package com.ipostu.demo13.credit.card.encryption.interceptor_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo13CreditCardEncryption {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local");
        SpringApplication.run(Demo13CreditCardEncryption.class, args);
    }

}