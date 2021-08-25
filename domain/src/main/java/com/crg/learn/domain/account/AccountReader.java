package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

public interface AccountReader {
    void accountNumber(String value);

    void ownerFirstName(String value);

    void ownerLastName(String value);

    void balance(Money value);
}
