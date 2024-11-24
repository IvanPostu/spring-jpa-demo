package com.ipostu.demo1.liquibase;

import static org.assertj.core.api.Assertions.assertThat;

import com.ipostu.demo2.flyway.repositories.BookRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootApplicationTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testBookRepository() {
        long count = bookRepository.count();

        assertThat(count).isGreaterThan(0);
    }

    @Test
    void contextLoads() {
    }

}