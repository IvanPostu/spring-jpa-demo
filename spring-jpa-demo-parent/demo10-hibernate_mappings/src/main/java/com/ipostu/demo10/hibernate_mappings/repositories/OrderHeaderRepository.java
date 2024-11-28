package com.ipostu.demo10.hibernate_mappings.repositories;

import com.ipostu.demo10.hibernate_mappings.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
