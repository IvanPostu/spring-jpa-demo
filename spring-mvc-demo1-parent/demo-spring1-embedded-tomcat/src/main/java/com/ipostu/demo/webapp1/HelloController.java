package com.ipostu.demo.webapp1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // http://localhost:8080/springapp/app
    @GetMapping
    @ResponseBody
    public String helloWorld() {
        return "<html><body>Hello world HTML</body></html>";
    }

}
