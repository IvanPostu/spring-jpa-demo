package com.ipostu.demo.spring.jar14demo.util;

import com.ipostu.demo.spring.jar14demo.model.Person;
import com.ipostu.demo.spring.jar14demo.services.PersonDetailsServiceImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsServiceImpl personDetailsService;

    public PersonValidator(PersonDetailsServiceImpl personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try{
            personDetailsService.loadUserByUsername(person.getUsername());
        }catch (UsernameNotFoundException e) {
            return;
        }
        errors.rejectValue("username", "", "User with the same username was found");
    }
}
