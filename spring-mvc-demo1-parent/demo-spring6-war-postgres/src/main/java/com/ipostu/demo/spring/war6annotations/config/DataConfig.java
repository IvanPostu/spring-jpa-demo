package com.ipostu.demo.spring.war6annotations.config;

import com.ipostu.demo.spring.war6annotations.dao.MyCustomConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db.properties")
public class DataConfig {

    @Bean
    MyCustomConnectionFactory myCustomConnectionFactory() {
        return new MyCustomConnectionFactory();
    }

}
