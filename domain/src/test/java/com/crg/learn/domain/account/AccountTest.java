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
                depositAmount = amountInDefaultCurrency(50.25);
                account.add(new Entry(depositAmount));
            }

            @Test
            @DisplayName("has balance equal to deposit amount after first deposit")
            void hasBalanceEqualToDepositAmount() {
                assertThat(account.hasBalanceOf(depositAmount)).isTrue();
            }

            @Test
            @DisplayName("has balance equal to the cumulative deposits")
            void hasBalanceEqualToCumulativeDeposits() {
                account.add(new Entry(amountInDefaultCurrency(1000)));
                account.add(new Entry(amountInDefaultCurrency(14.35)));

                assertThat(account.hasBalanceOf(amountInDefaultCurrency(1064.60))).isTrue();
            }
        }

        @Nested
        @DisplayName("after making withdrawals")
        class AfterMakingWithdrawals {
            @BeforeEach
            void init() {
                account.add(new Entry(amountInDefaultCurrency(1064.60)));
            }

            @Test
            @DisplayName("has balance equal to the previous balance minus the withdrawal amount")
            void hasBalanceEqualToPreviousBalanceMinusWithdrawalAmount() {
                account.add(new Entry(amountInDefaultCurrency(-100)));

                assertThat(account.hasBalanceOf(amountInDefaultCurrency(964.60))).isTrue();
            }
        }

    }

    private Money amountInDefaultCurrency(Number amount) {
        return Money.of(amount, DEFAULT_CURRENCY);
    }

}