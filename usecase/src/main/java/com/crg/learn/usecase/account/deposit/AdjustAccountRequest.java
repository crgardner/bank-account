package com.crg.learn.usecase.account.deposit;

public record AdjustAccountRequest(String accountNumber, double amount, String currency) {
}
