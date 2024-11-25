package com.ipostu.demo9.pagination.dao;

import com.ipostu.demo9.pagination.domain.Author;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface AuthorDao {

    List<Author> findAllAuthorsByLastName(String lastname, Pageable pageable);

    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);
}
