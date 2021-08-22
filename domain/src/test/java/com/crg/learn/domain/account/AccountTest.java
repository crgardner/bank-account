package com.crg.learn.domain.account;

import static org.assertj.core.api.Assertions.*;
import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;

import javax.money.*;

@DisplayName("Account")
class AccountTest {
    private static final CurrencyUnit DEFAULT_CURRENCY = Monetary.getCurrency("EUR");

    private Account account;

    @BeforeEach
    void init() {
        account = new Account(new AccountNumber("123X99948715"), new Person("Zippy", "Foo"));
    }

    @Nested
    @DisplayName("when created")
    class WhenCreated {

        @Test
        @DisplayName("has zero balance")
        void hasZeroBalance() {
            assertThat(account.hasBalanceOf(Money.zero(DEFAULT_CURRENCY))).isTrue();
        }

        @Nested
        @DisplayName("after making deposits")
        class AfterMakingDeposits {
            private Money depositAmount;

            @BeforeEach
            void init() {
                depositAmount = Money.of(50.25, DEFAULT_CURRENCY);
                account.add(new Entry(depositAmount));
            }

            @Test
            @DisplayName("has balance equal to deposit amount after first deposit")
            void hasBalanceEqualToDepositAmount() {
                assertThat(account.hasBalanceOf(depositAmount)).isTrue();
            }

            @Test
            @DisplayName("has balance equal to the cumulative deposits")
            void hasBalanceEqualToCumulativeSubsequentDeposits() {
                account.add(new Entry(Money.of(1000, DEFAULT_CURRENCY)));
                account.add(new Entry(Money.of(14.35, DEFAULT_CURRENCY)));

                assertThat(account.hasBalanceOf(Money.of(1064.60, DEFAULT_CURRENCY))).isTrue();
            }
        }

    }
}