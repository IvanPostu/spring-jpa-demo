package com.ipostu.demo.spring.war4annotations;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello1")
    public String hello1(HttpServletRequest request) {
        String name = request.getParameter("name");
        LOG.debug("Name: {}", name);
        return "hello_world";
    }

    @GetMapping("/hello2")
    public String hello2(@RequestParam(value = "name", required = false) String name) {
        LOG.debug("Name: {}", name);
        return "hello_world";
    }

}
