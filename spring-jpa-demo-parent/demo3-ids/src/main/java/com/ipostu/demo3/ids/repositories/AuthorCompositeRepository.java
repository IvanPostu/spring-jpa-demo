package com.ipostu.demo3.ids.repositories;

import com.ipostu.demo3.ids.domain.composite.AuthorComposite;
import com.ipostu.demo3.ids.domain.composite.NameId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}