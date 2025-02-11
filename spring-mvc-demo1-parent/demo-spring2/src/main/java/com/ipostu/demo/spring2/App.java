package com.ipostu.demo.spring2;

import com.ipostu.demo.spring2.package_to_scan2.Example1Config;
import com.ipostu.demo.spring2.package_to_scan3.Example2Config;
import com.ipostu.demo.spring2.xml.Music;
import com.ipostu.demo.spring2.xml.MusicPlayer;
import com.ipostu.demo.spring2.xml.PrototypeExample;
import com.ipostu.demo.spring2.xml.RockMusic;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        annotationDemo();
        // javaDemo();
        // xmlDemo();
    }

    private static void annotationDemo() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Example2Config.class);
        context.close();
    }

    private static void javaDemo() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Example1Config.class);
        context.close();
    }

    private static void xmlDemo() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );

        MusicPlayer musicPlayer = context.getBean("musicPlayer", MusicPlayer.class);

        musicPlayer.playMusic();

        System.out.println(musicPlayer.getName());
        System.out.println(musicPlayer.getVolume());

        System.out.println(
                context.getBean("prototypeExample", PrototypeExample.class)
                        == context.getBean("prototypeExample", PrototypeExample.class));

        System.out.println(
                context.getBean("musicBean", Music.class)
                        == context.getBean("musicBean", Music.class));

        BeanFactory beanFactory = context.getBeanFactory();

        // bean without id
        System.out.println(beanFactory.getBean("com.ipostu.demo.spring2.xml.RockMusic", RockMusic.class));

        context.close();
    }
}
