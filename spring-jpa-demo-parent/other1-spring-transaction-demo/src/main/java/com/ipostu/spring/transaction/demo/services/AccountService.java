package com.ipostu.spring.transaction.demo.services;

public interface AccountService {

    //    @Transactional
    void updateAccountFunds();

    //    @Transactional(propagation = Propagation.NESTED)
    void addFundsToAccount(int amount);

    void updateAccountFundsUsingTransactionTemplate();

    void addFundsToAccountUsingTransactionTemplate(int amount);

    void updateAccountFundsUsingTransactionManager();

    void addFundsToAccountUsingTransactionManager(int amount);
}
