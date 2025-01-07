package com.ipostu.spring.transaction.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public final class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final AccountService accountService;
    private final Supplier<AccountService> accountServiceSupplier;

    public AccountServiceImpl(JdbcTemplate jdbcTemplate, @Lazy AccountService accountService, BeanFactory beanFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountService = accountService;
        this.accountServiceSupplier = () -> beanFactory.getBean(AccountService.class);
    }

    @Override
    public void updateAccountFunds() {
        LOG.info("TX IN >>>");
        // jdbcTemplate.update("update t_account set c_balance = ? where id = 1", amount + 100);
        accountService.addFundsToAccount(200);
        Integer amount = jdbcTemplate.queryForObject("SELECT c_balance FROM t_account WHERE id=1;", Integer.class);
        LOG.info("<<< TX OUT, amount: {}", amount);
    }

    @Override
    public void addFundsToAccount(int amount) {
        LOG.info("TX IN >>>");
        jdbcTemplate.update("update t_account set c_balance = c_balance + ? where id=1;", Integer.valueOf(amount));
        LOG.info("<<< TX OUT");
    }
}
