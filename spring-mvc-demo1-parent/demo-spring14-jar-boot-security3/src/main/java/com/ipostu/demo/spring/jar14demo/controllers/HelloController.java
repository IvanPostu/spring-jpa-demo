package com.ipostu.demo.spring.jar14demo.controllers;

import com.ipostu.demo.spring.jar14demo.security.PersonDetails;
import com.ipostu.demo.spring.jar14demo.services.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    private final AdminService adminService;

    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "as_admin", required = false) boolean asAdmin) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        if (asAdmin) {
            adminService.doAdminStuff();
        }

        return "hello";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
