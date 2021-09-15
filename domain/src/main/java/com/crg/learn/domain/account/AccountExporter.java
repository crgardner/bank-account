package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;

public interface AccountExporter {
    void accountNumber(String value);

    void ownerFirstName(String value);

    void ownerLastName(String value);

    void balance(Money value);

    default void addEntry(Instant whenBooked, Money amount) {
        // no-op
    }
}
