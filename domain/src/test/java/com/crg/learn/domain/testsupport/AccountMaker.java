package com.crg.learn.domain.testsupport;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import com.natpryce.makeiteasy.*;

public class AccountMaker {
    public static final Property<Account, Person> accountHolder = Property.newProperty();
    public static final Property<Account, AccountNumber> number = Property.newProperty();
    public static final Property<Account, String> numberValue = Property.newProperty();


    public static final Instantiator<Account> Account = lookup ->
            new Account(accountNumberFrom(lookup), lookup.valueOf(accountHolder, new Person("Ford", "Prefect")));

    private static AccountNumber accountNumberFrom(PropertyLookup<Account> lookup) {
        var actualNumberValue = lookup.valueOf(numberValue, "123X99948715");

        return lookup.valueOf(number, new AccountNumber(actualNumberValue));
    }

}
