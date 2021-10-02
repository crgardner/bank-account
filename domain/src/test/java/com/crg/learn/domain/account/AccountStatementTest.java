package com.crg.learn.domain.account;

import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.crg.learn.domain.account.BookingDates.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("AccountStatement")
class AccountStatementTest {
    private AccountStatement statement;

    @BeforeEach
    void init() {
        var lines = List.of(
                new AccountStatementLine(amountInDefaultCurrency(100), jun_21_2021(), amountInDefaultCurrency(100)),
                new AccountStatementLine(amountInDefaultCurrency(150), jul_03_2021(), amountInDefaultCurrency(250)),
                new AccountStatementLine(amountInDefaultCurrency(-20), aug_20_2021(), amountInDefaultCurrency(230))
        );

        statement = new AccountStatement(new AccountNumber("123"), new Person("first", "last"),
                                         new AccountStatementLines(lines));
    }

    @Test
    void exports() {
        var exporter = new AccountStatementTestExporter();
        statement.export(exporter);

        assertThat(exporter.statementEntries()).containsExactly(
            new AccountStatementEntryExporter(jun_21_2021(), amountInDefaultCurrency(100), amountInDefaultCurrency(100)),
            new AccountStatementEntryExporter(jul_03_2021(), amountInDefaultCurrency(150), amountInDefaultCurrency(250)),
            new AccountStatementEntryExporter(aug_20_2021(), amountInDefaultCurrency(-20), amountInDefaultCurrency(230))
        );
    }

    private Money amountInDefaultCurrency(Number amount) {
        return Money.of(amount, "EUR");
    }
}