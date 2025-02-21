package com.ipostu.demo.spring.jar14demo.controllers;

import com.ipostu.demo.spring.jar14demo.model.Person;
import com.ipostu.demo.spring.jar14demo.services.RegisterService;
import com.ipostu.demo.spring.jar14demo.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {

    private final RegisterService registerService;
    private final PersonValidator personValidator;

    public LoginController(RegisterService registerService, PersonValidator personValidator) {
        this.registerService = registerService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loadPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("person") Person person) {

        return "auth/register";
    }

    @PostMapping("/register")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        registerService.register(person);
        return "redirect:/auth/login";
    }
}
