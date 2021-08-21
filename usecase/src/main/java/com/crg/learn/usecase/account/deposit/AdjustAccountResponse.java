package com.crg.learn.usecase.account.deposit;

import org.javamoney.moneta.Money;

public record AdjustAccountResponse(String accountNumber, Money balance) {
}
