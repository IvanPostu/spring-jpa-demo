package com.ipostu.demo.spring2.package_to_scan;

import com.ipostu.demo.spring2.xml.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanExample3SetterInjection {

    private Music music;

    @Autowired
    private void setMusic(Music music) {
        this.music = music;
    }

    public Music getMusic() {
        return music;
    }
}
