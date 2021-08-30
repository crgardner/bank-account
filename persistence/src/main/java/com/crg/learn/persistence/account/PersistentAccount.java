package com.crg.learn.persistence.account;

import org.javamoney.moneta.Money;

import javax.persistence.*;

@Entity
public class PersistentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQUENCE")
    @SequenceGenerator(name = "ACCOUNT_SEQUENCE", sequenceName = "ACCOUNT_SEQUENCE")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name="holder_first_name")
    private String holderFirstName;

    @Column(name = "holder_last_name")
    private String holderLastName;

    @Column(name = "balance")
    @Convert(converter = com.crg.learn.persistence.conversion.MoneyAttributeConverter.class)
    private Money balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderFirstName() {
        return holderFirstName;
    }

    public void setHolderFirstName(String holderFirstName) {
        this.holderFirstName = holderFirstName;
    }

    public String getHolderLastName() {
        return holderLastName;
    }

    public void setHolderLastName(String holderLastName) {
        this.holderLastName = holderLastName;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}
