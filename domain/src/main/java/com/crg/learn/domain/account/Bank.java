package com.crg.learn.domain.account;

import java.util.Optional;

public interface Bank {
    void open(Account account);
    void update(Account account);
    Optional<Account> lookup(AccountNumber accountNumber);
}
