package com.ipostu.demo10.hibernate_mappings.repositories;

import com.ipostu.demo10.hibernate_mappings.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByCustomerNameIgnoreCase(String customerName);
}
