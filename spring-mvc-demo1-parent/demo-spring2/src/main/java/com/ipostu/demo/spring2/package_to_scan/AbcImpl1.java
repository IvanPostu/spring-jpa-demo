package com.ipostu.demo.spring2.package_to_scan;

import org.springframework.stereotype.Component;

@Component
public class AbcImpl1 implements AbcInterface {
    private AbcImpl1() {
    }


    private static AbcImpl1 create() {
        System.out.println("AbcImpl1 static factory method");
        return new AbcImpl1();
    }

}
