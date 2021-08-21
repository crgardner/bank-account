package com.crg.learn.usecase.account.open;

import org.javamoney.moneta.Money;

public record OpenAccountResponse(String accountNumber, String ownerFirstName, String ownerLastName, Money balance) {
}
