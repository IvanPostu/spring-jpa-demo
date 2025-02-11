package com.ipostu.demo.spring2.package_to_scan2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:scan2.properties")
public class Example1Config {

    @Bean("prototypeExampleAbc")
    PrototypeExample prototypeExample() {
        return new PrototypeExample();
    }

    @Bean
    SingletonExample singletonExample1() {
        return new SingletonExample();
    }
}
