package com.ipostu.demo9.pagination.repositories;

import com.ipostu.demo9.pagination.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);

    // both work

    Page<Author> findAuthorByLastName(String lastName, Pageable pageable);

    @Query("SELECT a FROM Author a WHERE a.lastName = :lastName")
    Page<Author> findAuthorsByLastName(@Param("lastName") String lastName, Pageable pageable);
}
