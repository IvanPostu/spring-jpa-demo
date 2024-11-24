package com.ipostu.demo3.ids.repositories;

import com.ipostu.demo3.ids.domain.BookNatural;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
}
