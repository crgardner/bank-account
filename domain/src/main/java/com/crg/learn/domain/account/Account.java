package com.crg.learn.domain.account;

import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.util.Objects;

public class Account {
    private final AccountNumber accountNumber;
    private final Person accountHolder;
    private final Money balance = Money.of(0, Monetary.getCurrency("EUR"));

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

    public void writeTo(AccountReader reader) {
        reader.balance(balance);
        accountNumber.writeTo(reader::accountNumber);
        accountHolder.writeTo((first, last) -> {
            reader.ownerFirstName(first);
            reader.ownerLastName(last);
        });
    }

    public interface AccountReader {
        void accountNumber(String value);
        void ownerFirstName(String value);
        void ownerLastName(String value);
        void balance(Money value);
    }
}
