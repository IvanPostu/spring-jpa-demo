package com.ipostu.demo.spring2;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
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
        System.out.println(beanFactory.getBean("com.ipostu.demo.spring2.RockMusic", RockMusic.class));

        context.close();
    }
}
