package com.ipostu.demo8.jpa.query.dao;

import com.ipostu.demo8.jpa.query.domain.Book;

public interface BookDao {

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

}
