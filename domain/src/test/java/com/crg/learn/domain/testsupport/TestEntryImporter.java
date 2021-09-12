package com.crg.learn.domain.testsupport;

import com.crg.learn.domain.account.EntryImporter;
import org.javamoney.moneta.Money;

import java.time.Instant;

public record TestEntryImporter(Money amount, Instant whenBooked) implements EntryImporter {
}
