package com.ipostu.demo.spring2.xml;

public class BeanWithFactoryMethod {
    private BeanWithFactoryMethod() {
    }


    public static BeanWithFactoryMethod createStaticMethod() {
        return new BeanWithFactoryMethod();
    }

    private void initExample() {
        System.out.println("init method");
    }

    private void destroyExample() {
        System.out.println("destroy method");
    }
}
