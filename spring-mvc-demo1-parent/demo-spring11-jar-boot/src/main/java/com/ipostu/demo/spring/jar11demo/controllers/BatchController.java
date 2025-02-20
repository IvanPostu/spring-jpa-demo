package com.ipostu.demo.spring.jar11demo.controllers;

import com.ipostu.demo.spring.jar11demo.dao.PersonDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-batch-update")
public class BatchController {
    private final PersonDao personDao;

    public BatchController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping()
    public String index() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch(Model model) {
        long timeTaken = personDao.testMultipleUpdate();
        model.addAttribute("timeTaken", Long.valueOf(timeTaken));
        return "batch/index";
    }

    @GetMapping("/with")
    public String withBatch(Model model) {
        long timeTaken = personDao.testBatchUpdate();
        model.addAttribute("timeTaken", Long.valueOf(timeTaken));
        return "batch/index";
    }
}
