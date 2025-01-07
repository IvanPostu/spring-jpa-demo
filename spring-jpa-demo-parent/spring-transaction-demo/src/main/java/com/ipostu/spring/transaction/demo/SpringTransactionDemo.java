package com.ipostu.spring.transaction.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:application-context.xml")
public class SpringTransactionDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringTransactionDemo.class, args);
    }

}
