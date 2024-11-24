package com.ipostu.demo4.jdbc.repositories;

import com.ipostu.demo4.jdbc.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
