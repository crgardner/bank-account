package com.crg.learn.persistence.conversion;

import org.javamoney.moneta.Money;

import javax.money.Monetary;
import javax.persistence.AttributeConverter;

public class MoneyAttributeConverter implements AttributeConverter<Money, String> {

    @Override
    public String convertToDatabaseColumn(Money attribute) {
        return String.format("%s:%s", attribute.getNumber(), attribute.getCurrency().getCurrencyCode());
    }

    @Override
    public Money convertToEntityAttribute(String dbData) {
        var components = dbData.split(":");

        return Money.of(Double.parseDouble(components[0]), Monetary.getCurrency(components[1]));
    }

}
