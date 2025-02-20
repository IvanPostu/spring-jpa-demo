package com.ipostu.demo.spring.jar13demo.services;

import com.ipostu.demo.spring.jar13demo.model.Person;
import com.ipostu.demo.spring.jar13demo.repositories.PersonRepository;
import com.ipostu.demo.spring.jar13demo.security.PersonDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;

    public PersonDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found, username: " + username);
        }
        return new PersonDetails(person.get());
    }
}
