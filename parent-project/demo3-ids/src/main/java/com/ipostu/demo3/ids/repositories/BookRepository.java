package com.ipostu.demo3.ids.repositories;

import com.ipostu.demo3.ids.domain.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
