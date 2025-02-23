package com.ipostu.demo.spring.jar17demo.controllers;

import com.ipostu.demo.spring.jar17demo.controllers.rest.PersonNotCreatedException;
import com.ipostu.demo.spring.jar17demo.controllers.rest.PersonNotFoundResponse;
import com.ipostu.demo.spring.jar17demo.models.Person;
import com.ipostu.demo.spring.jar17demo.security.PersonDetails;
import com.ipostu.demo.spring.jar17demo.services.JwtService;
import com.ipostu.demo.spring.jar17demo.services.PeopleService;
import com.ipostu.demo.spring.jar17demo.services.PersonNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/people")
public class PeopleController {
    private static final Logger LOG = LoggerFactory.getLogger(PeopleController.class);

    private final PeopleService peopleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public PeopleController(PeopleService peopleService, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.peopleService = peopleService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Person> getPeople() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();

        LOG.info("Authenticated as {}", person.getUsername());
        List<Person> personList = peopleService.findAll();
        return Collections.unmodifiableList(personList);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        Person person = peopleService.findOne(id);
        return person;
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody @Valid Person person,
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

        person.setId(0);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleService.save(person);

        String token = jwtService.generateToken(person.getUsername());
        LOG.info("Person successfully created. Token: {}", token);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody Map<String, String> body) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                body.get("username"),
                body.get("password")
        );

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtService.generateToken(authInputToken.getName());
        return Map.of("token", token);
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
