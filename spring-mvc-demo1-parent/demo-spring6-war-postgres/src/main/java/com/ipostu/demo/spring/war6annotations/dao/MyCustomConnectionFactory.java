package com.ipostu.demo.spring.war6annotations.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyCustomConnectionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(MyCustomConnectionFactory.class);

    @Value("${pg.url}")
    private String url;
    @Value("${pg.username}")
    private String username;
    @Value("${pg.password}")
    private String password;

    public MyCustomConnectionFactory() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public Connection getConnection() {
        try {
            LOG.debug("Test: {}, {}, {}", url, username, password);
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LOG.error("Can't create a connection to db", e);
            throw new IllegalStateException(e);
        }
    }
}
