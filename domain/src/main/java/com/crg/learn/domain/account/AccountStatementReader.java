package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;

public interface AccountStatementReader {
    void addLine(Instant whenBooked, Money amount, Money balance);
}
