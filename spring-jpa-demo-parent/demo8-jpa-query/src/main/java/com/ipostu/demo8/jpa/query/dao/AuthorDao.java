package com.ipostu.demo8.jpa.query.dao;

import com.ipostu.demo8.jpa.query.domain.Author;

public interface AuthorDao {
    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);
}
