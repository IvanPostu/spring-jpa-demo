package com.ipostu.demo.spring.war10datajpa.repositories;

import com.ipostu.demo.spring.war10datajpa.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByEmail(String email);

}
