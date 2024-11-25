package com.ipostu.demo7.hibernate.queries.dao;

import com.ipostu.demo7.hibernate.queries.domain.Book;

import java.util.List;

public interface BookDao {
    Book findByISBN(String isbn);

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book findBookByTitleCriteria(String clean_code);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

    List<Book> findAll();
}
