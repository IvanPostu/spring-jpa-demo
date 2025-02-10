package com.ipostu.demo.spring2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext xmlContext = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );

        TestBean testBean = xmlContext.getBean(TestBean.class);
        System.out.println(testBean.getVal1());

        xmlContext.close();
    }
}
