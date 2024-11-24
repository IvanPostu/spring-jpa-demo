package com.ipostu.demo3.ids.repositories;

import com.ipostu.demo3.ids.domain.BookUuid;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
