package com.crg.learn.usecase.account.open;

import static org.assertj.core.api.Assertions.*;
import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.*;

import static org.mockito.Mockito.*;

@DisplayName("OpenAccount")
@ExtendWith(MockitoExtension.class)
class OpenAccountTest implements OpenAccountResponder {
    private OpenAccountResponse response;

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
        return new Account(new AccountNumber("011234567X"), new Person("Ford", "Prefect"));
    }

    private OpenAccountResponse expectedResponse() {
        return new OpenAccountResponse("011234567X", "Ford", "Prefect",
                                        Money.of(0, Monetary.getCurrency("EUR")));
    }

    @Override
    public void accept(OpenAccountResponse response) {
        this.response = response;
    }
}