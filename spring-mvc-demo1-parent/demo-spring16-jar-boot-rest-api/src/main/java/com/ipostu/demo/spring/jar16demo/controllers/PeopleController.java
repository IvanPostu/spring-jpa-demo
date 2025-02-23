package com.ipostu.demo.spring.jar16demo.controllers;

import com.ipostu.demo.spring.jar16demo.controllers.rest.PersonNotCreatedException;
import com.ipostu.demo.spring.jar16demo.controllers.rest.PersonNotFoundResponse;
import com.ipostu.demo.spring.jar16demo.controllers.rest.PersonRequest;
import com.ipostu.demo.spring.jar16demo.controllers.rest.PersonResponse;
import com.ipostu.demo.spring.jar16demo.models.Person;
import com.ipostu.demo.spring.jar16demo.services.PeopleService;
import com.ipostu.demo.spring.jar16demo.services.PersonNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PersonResponse> getPeople() {
        List<Person> personList = peopleService.findAll();
        return personList
                .stream()
                .map(this::mapToPersonResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public PersonResponse getPerson(@PathVariable("id") int id) {
        Person person = peopleService.findOne(id);
        return mapToPersonResponse(person);
    }

    @PostMapping
    public ResponseEntity<PersonResponse> create(@RequestBody @Valid PersonRequest personRequest,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new PersonNotCreatedException(errorMessage.toString());
        }

        Person person = mapToRequestToPerson(personRequest);
        peopleService.save(person);
        return new ResponseEntity<>(mapToPersonResponse(person), HttpStatus.CREATED);
    }

    private Person mapToRequestToPerson(PersonRequest personRequest) {
        return modelMapper.getTypeMap(PersonRequest.class, Person.class)
                .map(personRequest);
    }

    private PersonResponse mapToPersonResponse(Person person) {
        return modelMapper.getTypeMap(Person.class, PersonResponse.class).map(person);
    }

    @ExceptionHandler
    private ResponseEntity<PersonNotFoundResponse> handleException(PersonNotFoundException e) {
        PersonNotFoundResponse personNotFoundResponse = new PersonNotFoundResponse(
                "Person not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity(personNotFoundResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonNotFoundResponse> handleException(PersonNotCreatedException e) {
        PersonNotFoundResponse personNotFoundResponse = new PersonNotFoundResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity(personNotFoundResponse, HttpStatus.NOT_FOUND);
    }
}
