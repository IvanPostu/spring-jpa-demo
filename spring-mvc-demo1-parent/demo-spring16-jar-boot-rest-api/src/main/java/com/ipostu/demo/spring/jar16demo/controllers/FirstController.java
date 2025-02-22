package com.ipostu.demo.spring.jar16demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/first")
public class FirstController {

    @GetMapping
    @ResponseBody
    public String get() {
        return "FirstController";
    }

}
