package com.crg.learn.persistence.conversion;

import static org.assertj.core.api.Assertions.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;

import javax.money.Monetary;

@DisplayName("MoneyAttributeConverter")
class MoneyAttributeConverterTest {

    private MoneyAttributeConverter converter;
    private Money money;

    @BeforeEach
    void init() {
        converter = new MoneyAttributeConverter();
        money = Money.of(100.25, Monetary.getCurrency("EUR"));
    }

    @Test
    @DisplayName("converts money to string")
    void convertsMoneyToString() {
        var string = converter.convertToDatabaseColumn(money);

        assertThat(string).isEqualTo("100.25:EUR");
    }

    @Test
    @DisplayName("converts string to money")
    void convertsStringToMoney() {
        var convertedMoney = converter.convertToEntityAttribute("100.25:EUR");

        assertThat(convertedMoney).isEqualTo(money);
    }
}