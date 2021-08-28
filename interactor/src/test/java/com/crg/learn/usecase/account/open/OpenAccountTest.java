package com.crg.learn.usecase.account.open;

import static org.assertj.core.api.Assertions.*;
import com.crg.learn.domain.account.Account;
import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.shared.AccountResponse;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.Monetary;

import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.mockito.Mockito.*;

@DisplayName("OpenAccount")
@ExtendWith(MockitoExtension.class)
class OpenAccountTest implements OpenAccountResponder {
    private static final String ACCOUNT_NUMBER = "011234567X";

    private AccountResponse response;

    @Mock
    private Bank bank;

    @Test
    @DisplayName("creates an account")
    void createsAccount() {
        var request = new OpenAccountRequest("Ford", "Prefect");
        var useCase = new OpenAccount(bank);

        useCase.execute(request, this);

        assertThat(response).isEqualTo(expectedResponse());
        verify(bank).open(expectedAccount());
    }

    private Account expectedAccount() {
        return make(an(Account, with(numberValue, ACCOUNT_NUMBER)));
    }

    private AccountResponse expectedResponse() {
        return new AccountResponse(ACCOUNT_NUMBER, "Ford", "Prefect",
                                   Money.of(0, Monetary.getCurrency("EUR")));
    }

    @Override
    public void accept(AccountResponse response) {
        this.response = response;
    }
}