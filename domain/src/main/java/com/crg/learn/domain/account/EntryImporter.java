package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;

public interface EntryImporter {
    Money amount();

    Instant whenBooked();
}
