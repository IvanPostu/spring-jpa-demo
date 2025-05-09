package com.ipostu.demo2.flyway;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ipostu.demo2.flyway.repositories.BookRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.ipostu.demo1.flyway.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // in order to override default H2 config
                                                                             // provided by spring boot for test
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);

    }

}