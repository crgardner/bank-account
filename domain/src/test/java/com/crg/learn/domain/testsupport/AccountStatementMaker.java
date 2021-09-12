package com.crg.learn.domain.testsupport;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import com.natpryce.makeiteasy.*;

import java.util.*;

import static com.natpryce.makeiteasy.Property.*;

public class AccountStatementMaker {
    public static final Property<AccountStatement, Person> accountHolder = newProperty();
    public static final Property<AccountStatement, String> numberValue = newProperty();
    public static final Property<AccountStatement, List<AccountStatementLine>> lines = newProperty();

    public static final Instantiator<AccountStatement> AccountStatement = lookup -> {
        return new AccountStatement(accountNumberFrom(lookup),
                                    accountHolderFrom(lookup),
                                    linesFrom(lookup));
    };

    private static Person accountHolderFrom(PropertyLookup<com.crg.learn.domain.account.AccountStatement> lookup) {
        return lookup.valueOf(accountHolder, new Person("Ford", "Prefect"));
    }

    private static AccountStatementLines linesFrom(PropertyLookup<AccountStatement> lookup) {
        return new AccountStatementLines(lookup.valueOf(lines, Collections.emptyList()));
    }

    private static AccountNumber accountNumberFrom(PropertyLookup<AccountStatement> lookup) {
        var actualNumberValue = lookup.valueOf(numberValue, "123X99948715");

        return new AccountNumber(actualNumberValue);
    }

}
