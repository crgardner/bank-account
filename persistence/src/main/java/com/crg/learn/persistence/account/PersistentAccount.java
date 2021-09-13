package com.crg.learn.persistence.account;

import javax.persistence.*;
import java.util.*;

@Entity(name = "bank_account")
public class PersistentAccount {
    @Id
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name="holder_first_name")
    private String holderFirstName;

    @Column(name = "holder_last_name")
    private String holderLastName;

    @ElementCollection
    @CollectionTable(
            name = "entry",
            joinColumns = @JoinColumn(name = "account_number")
    )

    @SuppressWarnings("all")
    private List<PersistentEntry> entries = new ArrayList<>();

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

    public List<PersistentEntry> getEntries() {
        return entries;
    }

    public void add(PersistentEntry entry) {
        entries.add(entry);
    }
}
