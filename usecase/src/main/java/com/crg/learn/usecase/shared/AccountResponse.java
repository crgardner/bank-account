package com.crg.learn.usecase.shared;

import org.javamoney.moneta.Money;

public record AccountResponse(String accountNumber, String ownerFirstName, String ownerLastName, Money balance) {
}
