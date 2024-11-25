package com.ipostu.demo7.hibernate.queries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo7HibernateQueries {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local,default,clean");
        SpringApplication.run(Demo7HibernateQueries.class, args);
    }
}
