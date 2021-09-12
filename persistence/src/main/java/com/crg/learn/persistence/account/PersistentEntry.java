package com.crg.learn.persistence.account;

import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.time.Instant;

@Embeddable
public class PersistentEntry {
    @Column(name = "when_booked")
    private Instant whenBooked;

    @Column(name = "amount")
    @Convert(converter = com.crg.learn.persistence.conversion.MoneyAttributeConverter.class)
    private Money amount;

    public Instant getWhenBooked() {
        return whenBooked;
    }

    public void setWhenBooked(Instant whenBooked) {
        this.whenBooked = whenBooked;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

}
