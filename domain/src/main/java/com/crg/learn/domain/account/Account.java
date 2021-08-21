package com.crg.learn.domain.account;

import com.crg.learn.domain.person.Person;

import java.util.Objects;

public class Account {
    private final AccountNumber accountNumber;
    private final Person accountHolder;

    public Account(AccountNumber accountNumber, Person accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account account)) {
            return false;
        }

        return accountNumber.equals(account.accountNumber) && accountHolder.equals(account.accountHolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountHolder);
    }
}
