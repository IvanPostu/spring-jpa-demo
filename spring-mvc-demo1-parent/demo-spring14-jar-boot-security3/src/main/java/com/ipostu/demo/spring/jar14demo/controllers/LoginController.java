package com.ipostu.demo.spring.jar14demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @GetMapping("/login")
    public String loadPage() {
        return "auth/login";
    }
}
