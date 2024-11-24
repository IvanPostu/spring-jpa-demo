package com.ipostu.demo6.hibernate.dao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo6HibernateDao {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local,default,clean");
        SpringApplication.run(Demo6HibernateDao.class, args);
    }
}
