package com.ipostu.demo9.pagination.dao;

import com.ipostu.demo9.pagination.domain.Book;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface BookDao {

    List<Book> findAllBooksSortByTitle(Pageable pageable);

    List<Book> findAllBooks(Pageable pageable);

    List<Book> findAllBooks(int pageSize, int offset);

    List<Book> findAllBooks();

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

}
