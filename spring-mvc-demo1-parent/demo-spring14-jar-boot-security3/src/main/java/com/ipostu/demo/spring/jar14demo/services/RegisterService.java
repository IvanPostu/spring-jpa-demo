package com.ipostu.demo.spring.jar14demo.services;

import com.ipostu.demo.spring.jar14demo.model.Person;
import com.ipostu.demo.spring.jar14demo.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private final PersonRepository personRepository;

    public RegisterService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public void register(Person person) {
        personRepository.save(person);
    }
}
