package com.ipostu.demo.spring.jar11demo.controllers;

import com.ipostu.demo.spring.jar11demo.dao.PersonDao;
import com.ipostu.demo.spring.jar11demo.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);


    private final PersonDao personDao;

    public AdminController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String adminPage(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", personDao.index());

        return "admin/index";
    }

    @PatchMapping("/add")
    public String makeAdmin(@ModelAttribute("person") Person person) {
        LOG.info("Person with id {} is admin now!", person.getId());
        return "redirect:/people";
    }
}
