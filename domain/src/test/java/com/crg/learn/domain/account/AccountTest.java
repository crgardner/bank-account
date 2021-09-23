package com.crg.learn.domain.account;

import com.crg.learn.domain.person.Person;
import com.crg.learn.domain.testsupport.TestEntryImporter;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;

import javax.money.*;
import java.time.Instant;
import java.util.*;

import static com.crg.learn.domain.account.BookingDates.*;
import static org.assertj.core.api.Assertions.*;


@DisplayName("Account")
class AccountTest {
    private static final CurrencyUnit DEFAULT_CURRENCY = Monetary.getCurrency("EUR");

    private Account account;
    private AccountNumber accountNumber;
    private Person accountHolder;

    @BeforeEach
    void init() {
        accountNumber = new AccountNumber("123X99948715");
        accountHolder = new Person("Zippy", "Foo");
        account = new Account(accountNumber, accountHolder);
    }

    @Nested
    @DisplayName("when created")
    class WhenCreated {

        @Test
        @DisplayName("has zero balance")
        void hasZeroBalance() {
            var exporter = new AccountTestExporter();
            account.export(exporter);

            assertThat(exporter.balance).isEqualTo(Money.zero(DEFAULT_CURRENCY));
        }

        @Nested
        @DisplayName("after making deposits")
        class AfterMakingDeposits {
            private Money depositAmount;

            @BeforeEach
            void init() {
                depositAmount = amountInDefaultCurrency(50.25);
                account.add(new Entry(transactionId(), depositAmount));
            }

            @Test
            @DisplayName("has balance equal to deposit amount after first deposit")
            void hasBalanceEqualToDepositAmount() {
                var exporter = new AccountTestExporter();
                account.export(exporter);

                assertThat(exporter.balance).isEqualTo(depositAmount);
            }

            @Test
            @DisplayName("has balance equal to the cumulative deposits")
            void hasBalanceEqualToCumulativeDeposits() {
                account.add(new Entry(transactionId(), amountInDefaultCurrency(1000)));
                account.add(new Entry(transactionId(), amountInDefaultCurrency(14.35)));

                var exporter = new AccountTestExporter();
                account.export(exporter);

                assertThat(exporter.balance).isEqualTo(amountInDefaultCurrency(1064.60));
            }
        }

        @Nested
        @DisplayName("after making withdrawals")
        class AfterMakingWithdrawals {
            @BeforeEach
            void init() {
                account.add(new Entry(transactionId(), amountInDefaultCurrency(1064.60)));
            }

            @Test
            @DisplayName("has balance equal to the previous balance minus the withdrawal amount")
            void hasBalanceEqualToPreviousBalanceMinusWithdrawalAmount() {
                account.add(new Entry(transactionId(), amountInDefaultCurrency(-100)));

                var exporter = new AccountTestExporter();
                account.export(exporter);

                assertThat(exporter.balance).isEqualTo(amountInDefaultCurrency(964.60));
            }
        }
    }

    @Test
    @DisplayName("creates statement with all entries")
    void createsStatementIncludingAllEntries() {
        account.add(new Entry(transactionId(), amountInDefaultCurrency(100), jun_21_2021()));
        account.add(new Entry(transactionId(), amountInDefaultCurrency(150), jul_03_2021()));
        account.add(new Entry(transactionId(), amountInDefaultCurrency(-20), aug_20_2021()));

        var statement = account.createStatement();

        assertThat(statement).usingRecursiveComparison().isEqualTo(expectedStatement());
    }

    @Test
    @DisplayName("supports creation via import")
    void supportsCreationViaImport() {
        account = new Account(importer());
        var exporter = new AccountTestExporter();

        account.export(exporter);

        assertThat(exporter.accountNumber).isEqualTo("123");
        assertThat(exporter.balance).isEqualTo(amountInDefaultCurrency(100));
        assertThat(exporter.ownerFirstName).isEqualTo("Zippy");
        assertThat(exporter.ownerLastName).isEqualTo("Foo");
    }

    private AccountImporter importer() {
        return new AccountImporter() {

            @Override
            public AccountNumber accountNumber() {
                return new AccountNumber("123");
            }

            public Person accountHolder() {
                return new Person("Zippy", "Foo");
            }

            public List<EntryImporter> entryImporters() {
                return List.of(new TestEntryImporter(null, amountInDefaultCurrency(100), jun_21_2021()));
            }
        };
    }

    private AccountStatement expectedStatement() {
        var lines = List.of(
            new AccountStatementLine(amountInDefaultCurrency(100), jun_21_2021(), amountInDefaultCurrency(100)),
            new AccountStatementLine(amountInDefaultCurrency(150), jul_03_2021(), amountInDefaultCurrency(250)),
            new AccountStatementLine(amountInDefaultCurrency(-20), aug_20_2021(), amountInDefaultCurrency(230))
        );

        return new AccountStatement(accountNumber, accountHolder, new AccountStatementLines(lines));
    }

    private Money amountInDefaultCurrency(Number amount) {
        return Money.of(amount, DEFAULT_CURRENCY);
    }

    private static class AccountTestExporter implements AccountExporter {

        private String accountNumber;
        private String ownerFirstName;
        private String ownerLastName;
        private Money balance;

        @Override
        public void accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        @Override
        public void ownerFirstName(String ownerFirstName) {
            this.ownerFirstName = ownerFirstName;
        }

        @Override
        public void ownerLastName(String ownerLastName) {
            this.ownerLastName = ownerLastName;
        }

        @Override
        public void balance(Money balance) {
            this.balance = balance;
        }

    }

    private TransactionId transactionId() {
        return new TransactionId(UUID.randomUUID().toString());
    }

}