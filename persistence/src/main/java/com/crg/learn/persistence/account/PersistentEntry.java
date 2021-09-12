package com.crg.learn.persistence.account;

import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "entry")
public class PersistentEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTRY_SEQUENCE")
    @SequenceGenerator(name = "ENTRY_SEQUENCE", sequenceName = "ENTRY_SEQUENCE")
    @Column(name = "entry_id")
    private Long id;

    @Column(name = "when_booked")
    private Instant whenBooked;

    @Column(name = "amount")
    @Convert(converter = com.crg.learn.persistence.conversion.MoneyAttributeConverter.class)
    private Money amount;

    @ManyToOne
    @JoinColumn(name="bank_account_id")
    private PersistentAccount bankAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PersistentAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(PersistentAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
