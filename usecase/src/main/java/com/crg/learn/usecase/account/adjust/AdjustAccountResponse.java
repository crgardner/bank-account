package com.crg.learn.usecase.account.adjust;

import org.javamoney.moneta.Money;

public record AdjustAccountResponse(String accountNumber, Money balance) {
}
