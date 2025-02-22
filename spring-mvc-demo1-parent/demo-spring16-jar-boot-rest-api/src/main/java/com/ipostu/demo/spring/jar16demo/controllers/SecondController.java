package com.ipostu.demo.spring.jar16demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/second")
public class SecondController {

    @GetMapping
    public String get() {
        return "SecondController";
    }

}

