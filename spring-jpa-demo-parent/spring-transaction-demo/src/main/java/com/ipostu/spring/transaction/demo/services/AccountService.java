package com.ipostu.spring.transaction.demo.services;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {

    @Transactional
    void updateAccountFunds();

    @Transactional(propagation = Propagation.NESTED)
    void addFundsToAccount(int amount);
}
