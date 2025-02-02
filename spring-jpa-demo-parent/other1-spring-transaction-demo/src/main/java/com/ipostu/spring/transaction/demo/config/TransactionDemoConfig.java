package com.ipostu.spring.transaction.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class TransactionDemoConfig {

    @Bean
    @ConfigurationProperties("spring.transactiondemo.datasource")
    public DataSourceProperties transactionDemoHolderDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource transactionDemoDataSource(
        @Qualifier("transactionDemoHolderDataSourceProperties") DataSourceProperties transactionDemoHolderDataSourceProperties) {
        return transactionDemoHolderDataSourceProperties.initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("transactionDemoDataSource") DataSource transactionDemoDataSource) {
        return new JdbcTemplate(transactionDemoDataSource);
    }
}
