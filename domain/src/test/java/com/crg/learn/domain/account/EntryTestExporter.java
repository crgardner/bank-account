package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;

public record EntryTestExporter(String transactionId, Instant whenBooked, Money amount) {
}
