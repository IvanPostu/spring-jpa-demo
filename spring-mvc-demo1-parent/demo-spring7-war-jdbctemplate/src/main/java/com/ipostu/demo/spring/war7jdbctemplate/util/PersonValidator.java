package com.ipostu.demo.spring.war7jdbctemplate.util;

import com.ipostu.demo.spring.war7jdbctemplate.dao.PersonDao;
import com.ipostu.demo.spring.war7jdbctemplate.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDao personDao;

    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDao.selectByEmail(person.getEmail()) != null) {
            errors.rejectValue("email", "", "This email is already in use");
        }

        if (!Character.isUpperCase(person.getName().codePointAt(0)))
            errors.rejectValue("name", "", "Name should start with a capital letter");
    }
}
