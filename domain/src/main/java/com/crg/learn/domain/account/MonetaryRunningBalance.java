package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;

public class MonetaryRunningBalance {
    private Money balance;

    public MonetaryRunningBalance(CurrencyUnit currency) {
        this.balance = Money.zero(currency);
    }

    public Money adjust(Money amount) {
        balance = balance.add(amount);

        return balance;
    }
}
