package com.ipostu.demo.spring2.package_to_scan3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SingletonExample {

    @Value("${example.abc1}")
    private String value;
}
