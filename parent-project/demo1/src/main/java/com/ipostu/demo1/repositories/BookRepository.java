package com.ipostu.demo1.repositories;

import com.ipostu.demo1.domain.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
