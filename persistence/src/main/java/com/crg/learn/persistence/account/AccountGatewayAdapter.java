package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.*;

import java.util.Optional;

public class AccountGatewayAdapter implements Bank {
    private final AccountJpaRepository repository;

    public AccountGatewayAdapter(AccountJpaRepository repository) {
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
        var value = new AccountNumberValue(accountNumber).value();
        var possiblePersistentAccount = repository.findByAccountNumber(value);

        return possiblePersistentAccount.map(persistentAccount -> new Account(new AccountMapper(persistentAccount)));
    }

    private static class AccountNumberValue implements AccountNumber.AccountNumberReader {
        private final AccountNumber accountNumber;
        private String value;

        private AccountNumberValue(AccountNumber accountNumber) {
            this.accountNumber = accountNumber;
        }

        private String value() {
            accountNumber.writeTo(this);
            return value;
        }

        @Override
        public void numberValue(String value) {
            this.value = value;
        }
    }
}
