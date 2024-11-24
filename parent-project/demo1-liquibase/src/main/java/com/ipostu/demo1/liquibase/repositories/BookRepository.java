package com.ipostu.demo1.liquibase.repositories;

import com.ipostu.demo1.liquibase.domain.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
