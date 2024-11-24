package com.ipostu.demo3.ids.repositories;

import com.ipostu.demo3.ids.domain.composite.AuthorEmbedded;
import com.ipostu.demo3.ids.domain.composite.NameId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
