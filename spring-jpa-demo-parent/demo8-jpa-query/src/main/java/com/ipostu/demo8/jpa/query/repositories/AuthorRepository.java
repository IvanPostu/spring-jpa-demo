package com.ipostu.demo8.jpa.query.repositories;

import com.ipostu.demo8.jpa.query.domain.Author;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);
}
