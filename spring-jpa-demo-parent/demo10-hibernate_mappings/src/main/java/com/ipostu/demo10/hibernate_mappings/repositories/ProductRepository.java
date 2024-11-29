package com.ipostu.demo10.hibernate_mappings.repositories;

import com.ipostu.demo10.hibernate_mappings.domain.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByDescription(String description);
}
