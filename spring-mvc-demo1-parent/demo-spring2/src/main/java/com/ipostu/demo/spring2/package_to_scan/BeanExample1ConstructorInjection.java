package com.ipostu.demo.spring2.package_to_scan;

import com.ipostu.demo.spring2.xml.Music;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BeanExample1ConstructorInjection {

    private final Music music;
    private final AbcInterface abcInterface;

    @Autowired // not required since spring 4.3
    public BeanExample1ConstructorInjection(Music music, @Qualifier("abcImpl1") AbcInterface abcInterface) {
        this.music = music;
        this.abcInterface = abcInterface;
    }

    @PostConstruct
    private void init() {
        System.out.println("@PostConstruct init()");
    }

    @PreDestroy
    private void destroy() {
        System.out.println("@PreDestroy init()");
    }

    public Music getMusic() {
        return music;
    }
}
