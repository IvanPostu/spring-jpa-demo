package com.ipostu.demo.spring2.package_to_scan;

import com.ipostu.demo.spring2.xml.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanExample2FieldInjection {

    @Autowired
    private Music music;

    public Music getMusic() {
        return music;
    }
}
