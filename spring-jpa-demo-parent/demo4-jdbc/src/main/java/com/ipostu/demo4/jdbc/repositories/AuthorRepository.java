package com.ipostu.demo4.jdbc.repositories;

import com.ipostu.demo4.jdbc.domain.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
