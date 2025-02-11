package com.ipostu.demo.spring2.package_to_scan3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:scan2.properties")
public class Example2Config {

}
