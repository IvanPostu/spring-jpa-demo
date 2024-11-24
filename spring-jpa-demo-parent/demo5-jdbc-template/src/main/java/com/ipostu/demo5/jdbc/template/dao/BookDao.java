package com.ipostu.demo5.jdbc.template.dao;

import com.ipostu.demo5.jdbc.template.domain.Book;

public interface BookDao {
    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);
}
