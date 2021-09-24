package com.crg.learning.controller.account.adjust;

import org.javamoney.moneta.Money;

public record AdjustmentViewModel(String accountNumber, String balance, String currency, String transactionId, String amount) {

}
