package com.crg.learn.account.application.config;

import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.account.open.*;
import org.springframework.context.annotation.*;

import java.util.*;

@Configuration
public class InteractorConfiguration {

    @Bean
    OpenAccountUseCase openAccountUseCase(Bank bank) {
        return new OpenAccountInteractor(bank);
    }

    @Bean
    Bank bank() {
        return new Bank() {
            private final List<Account> accounts = new ArrayList<>();

            @Override
            public void open(Account account) {
                accounts.add(account);
            }

            @Override
            public void update(Account account) {
                accounts.add(account);
            }

            @Override
            public Optional<Account> lookup(AccountNumber accountNumber) {
                return Optional.of(accounts.get(0));
            }
        };
    }
}
