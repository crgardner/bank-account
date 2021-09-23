package com.crg.learn.persistence.account;

import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "entry")
public class PersistentEntry {
    @Column(name = "when_booked")
    private Instant whenBooked;

    @Column(name = "amount")
    @Convert(converter = com.crg.learn.persistence.conversion.MoneyAttributeConverter.class)
    private Money amount;

    @Id
    @Column(name="transaction_id")
    private String transactionId;

    @ManyToOne
    @JoinColumn(name="account_number")
    private PersistentAccount account;

    public PersistentAccount getAccount() {
        return account;
    }

    public void setAccount(PersistentAccount account) {
        this.account = account;
    }

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

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
