package com.ipostu.demo10.hibernate_mappings.repositories;

import com.ipostu.demo10.hibernate_mappings.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByDescription(String description);
}
