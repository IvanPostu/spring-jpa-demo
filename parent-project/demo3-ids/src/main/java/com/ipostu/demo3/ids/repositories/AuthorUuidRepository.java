package com.ipostu.demo3.ids.repositories;

import com.ipostu.demo3.ids.domain.AuthorUuid;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, UUID> {
}
