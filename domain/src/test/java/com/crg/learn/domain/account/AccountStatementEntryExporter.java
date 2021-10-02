package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;

record AccountStatementEntryExporter(Instant whenBooked, Money amount, Money balance) {
}
