package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.*;

import java.util.Optional;

public class AccountGateway implements AccountRepository {
    private final AccountJpaRepository repository;

    public AccountGateway(AccountJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void open(Account account) {
        update(account);
    }

    @Override
    public void update(Account account) {
        var mapper = new PersistentAccountMapper(account);
        var persistentAccount = mapper.map();

        repository.save(persistentAccount);
    }

    @Override
    public Optional<Account> lookup(AccountNumber accountNumber) {
        var possiblePersistentAccount = repository.findByAccountNumber(accountNumber.value());

        return possiblePersistentAccount.map(this::toAccount);
    }

    private Account toAccount(PersistentAccount persistentAccount) {
        return new Account(new AccountMapper(persistentAccount));
    }

}
