package com.crg.learn.domain.account;

import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;

public interface AccountImporter {
    AccountNumber accountNumber();
    Money accountBalance();
    Person accountHolder();

}
