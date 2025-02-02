package com.ipostu.spring.transaction.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

@Service
public final class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final AccountService accountService;
    private final Supplier<AccountService> accountServiceSupplier;
    private final TransactionTemplate transactionTemplate;
    private final PlatformTransactionManager transactionManager;

    public AccountServiceImpl(JdbcTemplate jdbcTemplate, @Lazy AccountService accountService, BeanFactory beanFactory, TransactionTemplate transactionTemplate, PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountService = accountService;
        this.accountServiceSupplier = () -> beanFactory.getBean(AccountService.class);
        this.transactionTemplate = transactionTemplate;
        this.transactionManager = transactionManager;
    }

    @Override
    public void updateAccountFunds() {
        LOG.info("TX IN >>>");
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

    @Override
    public void updateAccountFundsUsingTransactionTemplate() {
        transactionTemplate.executeWithoutResult((transactionStatus) -> {
            LOG.info("TX IN >>>");
            accountService.addFundsToAccount(200);
            Integer amount = jdbcTemplate.queryForObject("SELECT c_balance FROM t_account WHERE id=1;", Integer.class);
            LOG.info("<<< TX OUT, amount: {}", amount);
        });
    }

    @Override
    public void addFundsToAccountUsingTransactionTemplate(int amount) {
        transactionTemplate.executeWithoutResult((transactionStatus) -> {
            LOG.info("TX IN >>>");
            jdbcTemplate.update("update t_account set c_balance = c_balance + ? where id=1;", Integer.valueOf(amount));
            LOG.info("<<< TX OUT");
        });
    }

    @Override
    public void updateAccountFundsUsingTransactionManager() {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        LOG.info("TX IN >>>");
        accountService.addFundsToAccount(200);
        Integer amount = jdbcTemplate.queryForObject("SELECT c_balance FROM t_account WHERE id=1;", Integer.class);
        LOG.info("<<< TX OUT, amount: {}", amount);

        if (transactionStatus.isRollbackOnly()) {
            transactionManager.rollback(transactionStatus);
        } else {
            transactionManager.commit(transactionStatus);
        }
    }

    @Override
    public void addFundsToAccountUsingTransactionManager(int amount) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        LOG.info("TX IN >>>");
        jdbcTemplate.update("update t_account set c_balance = c_balance + ? where id=1;", Integer.valueOf(amount));
        LOG.info("<<< TX OUT");

        if (transactionStatus.isRollbackOnly()) {
            transactionManager.rollback(transactionStatus);
        } else {
            transactionManager.commit(transactionStatus);
        }
    }
}
