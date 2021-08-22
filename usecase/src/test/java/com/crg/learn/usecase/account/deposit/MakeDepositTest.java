package com.crg.learn.usecase.account.deposit;

import static org.assertj.core.api.Assertions.*;
import com.crg.learn.domain.account.Account;
import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.account.shared.AccountResponse;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.*;
import java.util.Optional;

import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.mockito.Mockito.*;

@DisplayName("MakeDeposit")
@ExtendWith(MockitoExtension.class)
class MakeDepositTest implements AdjustAccountResponder {
    private static final String ACCOUNT_NUMBER = "011234567X";
    private static final CurrencyUnit CURRENCY = Monetary.getCurrency("EUR");

    @Mock
    private Bank bank;

    private AccountResponse response;
    private AccountNumber accountNumber;
    private Account account;
    private AdjustAccountRequest adjustAccountRequest;
    private MakeDeposit useCase;
    private boolean accountNotFound;

    @BeforeEach
    void init() {
        accountNumber = new AccountNumber(ACCOUNT_NUMBER);
        account = an(Account, with(number, accountNumber)).value();
        adjustAccountRequest = new AdjustAccountRequest(ACCOUNT_NUMBER, 50.20, "EUR");
        useCase = new MakeDeposit(bank);
    }

    @Test
    @DisplayName("makes deposits to accounts")
    void makesDepositsToAccounts() {
        when(bank.lookup(accountNumber)).thenReturn(Optional.of(account));

        useCase.execute(adjustAccountRequest, this);

        assertThat(response).isEqualTo(new AccountResponse(ACCOUNT_NUMBER, "Ford", "Prefect",
                                                           Money.of(50.20, CURRENCY)));
        verify(bank).update(account);
    }

    @Override
    public void accept(AccountResponse response) {
        this.response = response;
    }

    @Override
    public void onNotFound() {
        this.accountNotFound = true;
    }

    @Test
    @DisplayName("reports account not found when no matching account number")
    void reportsAccountNotFoundWhenNoMatchingAccountNumber() {
        when(bank.lookup(accountNumber)).thenReturn(Optional.empty());

        useCase.execute(adjustAccountRequest, this);

        assertThat(accountNotFound).isTrue();
    }
}