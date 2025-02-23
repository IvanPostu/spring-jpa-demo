package com.ipostu.demo.spring.jar17demo.security;

import com.ipostu.demo.spring.jar17demo.models.Person;
import com.ipostu.demo.spring.jar17demo.repositories.PeopleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsServiceImpl implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    public PersonDetailsServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found, username: " + username);
        }
        return new PersonDetails(person.get());
    }
}
