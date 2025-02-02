package com.ipostu.spring.transaction.demo.runner;

import com.ipostu.spring.transaction.demo.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyCommandLineRunner {

    @Bean
    public CommandLineRunner commandLineRunner(AccountService accountService) {
        return args -> {
            accountService.updateAccountFundsUsingTransactionManager();
        };
    }

}
