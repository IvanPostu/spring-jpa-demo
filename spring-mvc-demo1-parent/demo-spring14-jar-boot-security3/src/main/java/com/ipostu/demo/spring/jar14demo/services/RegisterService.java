package com.ipostu.demo.spring.jar14demo.services;

import com.ipostu.demo.spring.jar14demo.model.Person;
import com.ipostu.demo.spring.jar14demo.repositories.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private static final String DEFAULT_INITIAL_ROLE = "ROLE_USER";

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        String hashedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(hashedPassword);
        person.setRole(DEFAULT_INITIAL_ROLE);

        personRepository.save(person);
    }
}
