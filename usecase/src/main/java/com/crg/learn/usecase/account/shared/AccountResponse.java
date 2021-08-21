package com.crg.learn.usecase.account.shared;

import org.javamoney.moneta.Money;

public record AccountResponse(String accountNumber, String ownerFirstName, String ownerLastName, Money balance) {
}
