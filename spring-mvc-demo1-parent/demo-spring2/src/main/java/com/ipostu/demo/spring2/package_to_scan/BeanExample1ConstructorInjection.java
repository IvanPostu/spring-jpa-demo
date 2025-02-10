package com.ipostu.demo.spring2.package_to_scan;

import com.ipostu.demo.spring2.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanExample1ConstructorInjection {

    private final Music music;

    @Autowired // not required since spring 4.3
    public BeanExample1ConstructorInjection(Music music) {
        this.music = music;
    }

    public Music getMusic() {
        return music;
    }
}
