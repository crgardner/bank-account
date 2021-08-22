package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;

public class Entry {
    private final Money amount;
    private final Instant whenBooked;

    public Entry(Money amount) {
        this.amount = amount;
        this.whenBooked = Instant.now();
    }

    public Money adjust(Money balance) {
        return balance.add(amount);
    }
}
