package com.crg.learn.domain.account;

import static org.assertj.core.api.Assertions.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;

import javax.money.*;
import javax.money.convert.*;

@DisplayName("AccountStatement")
class AccountStatementTest {

    @Test
    void test() {
        var usd = Money.of(587.87, Monetary.getCurrency("USD"));

//        var eurConversion = MonetaryConversions.getConversion("EUR");

        CurrencyConversion currencyConversion;

        var euros = usd.multiply(.85);
        System.out.println(euros);
    }
}