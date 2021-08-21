package com.crg.learn.usecase.account;

import static org.assertj.core.api.Assertions.*;
import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@DisplayName("AddAccount")
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

        assertThat(response).isEqualTo(new OpenAccountResponse("011234567X"));
        verify(bank).open(new Account(new AccountNumber("011234567X"), new Person("Ford", "Prefect")));
    }

    @Override
    public void accept(OpenAccountResponse response) {
        this.response = response;
    }
}