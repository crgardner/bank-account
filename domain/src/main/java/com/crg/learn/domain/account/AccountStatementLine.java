package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;

public class AccountStatementLine {
    private final Money amount;
    private final Instant whenBooked;
    private final Money balance;

    public AccountStatementLine(Money amount, Instant whenBooked, Money balance) {
        this.amount = amount;
        this.whenBooked = whenBooked;
        this.balance = balance;
    }

    public void writeTo(AccountStatementReader reader) {
        reader.addLine(whenBooked, amount, balance);
    }
}
