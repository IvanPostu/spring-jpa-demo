package com.ipostu.demo.spring.war10datajpa.util;

import com.ipostu.demo.spring.war10datajpa.models.Person;
import com.ipostu.demo.spring.war10datajpa.repositories.PeopleRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PeopleRepository peopleRepository;

    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        
        if (peopleRepository.findByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already in use");
        }

        if (!Character.isUpperCase(person.getName().codePointAt(0)))
            errors.rejectValue("name", "", "Name should start with a capital letter");
    }
}
