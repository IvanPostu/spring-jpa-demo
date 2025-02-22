package com.ipostu.demo.spring.jar16demo.controllers;

import com.ipostu.demo.spring.jar16demo.controllers.rest.PersonNotFoundResponse;
import com.ipostu.demo.spring.jar16demo.models.Person;
import com.ipostu.demo.spring.jar16demo.services.PeopleService;
import com.ipostu.demo.spring.jar16demo.services.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<Person> getPeople() {
        return peopleService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return peopleService.findOne(id);
    }

    @ExceptionHandler
    private ResponseEntity<PersonNotFoundResponse> handleException(PersonNotFoundException e) {
        PersonNotFoundResponse personNotFoundResponse = new PersonNotFoundResponse(
                "Person not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity(personNotFoundResponse, HttpStatus.NOT_FOUND);
    }
}
