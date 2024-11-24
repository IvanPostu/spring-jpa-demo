package com.ipostu.demo2.flyway.repositories;

import com.ipostu.demo2.flyway.domain.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
