package com.crg.learn.usecase.account.statement;

import com.crg.learn.domain.account.Account;
import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.concept.UseCase;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.*;
import java.time.*;
import java.util.*;

import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("PrepareAccountStatementInteractor")
@ExtendWith(MockitoExtension.class)
class PrepareAccountStatementInteractorTest implements PrepareAccountStatementResponder {
    private static final String ACCOUNT_NUMBER = "011234567X";
    private static final String CURRENCY_VALUE = "EUR";
    private static final CurrencyUnit CURRENCY = Monetary.getCurrency(CURRENCY_VALUE);

    @Mock
    private Bank bank;

    private AccountNumber accountNumber;
    private Account account;
    private UseCase<PrepareAccountStatementRequest, PrepareAccountStatementResponder> useCase;
    private PrepareStatementResponse response;
    private Instant june_21_2021;
    private Instant july_7_2021;

    @BeforeEach
    void init() {
        accountNumber = new AccountNumber(ACCOUNT_NUMBER);
        account = make(an(Account, with(number, accountNumber)));
        useCase = new PrepareAccountStatementInteractor(bank);
        june_21_2021 = LocalDateTime.of(2021, 6, 21, 10, 0).toInstant(ZoneOffset.UTC);
        july_7_2021 = LocalDateTime.of(2021, 7, 7, 14, 30).toInstant(ZoneOffset.UTC);
    }

    @Test
    @DisplayName("prepares statement for account")
    void preparesStatementForAccount() {
        account.add(new Entry(Money.of(100, CURRENCY), june_21_2021));
        account.add(new Entry(Money.of(-50, CURRENCY), july_7_2021));
        when(bank.lookup(accountNumber)).thenReturn(Optional.of(account));

        PrepareAccountStatementRequest request = new PrepareAccountStatementRequest(ACCOUNT_NUMBER);
        useCase.execute(request, this);

        assertThat(response).isEqualTo(expectedResponse());
    }

    private PrepareStatementResponse expectedResponse() {

        return new PrepareStatementResponse(
                List.of(
                        new PrepareStatementResponseLine(june_21_2021, Money.of(100, CURRENCY), Money.of(100, CURRENCY)),
                        new PrepareStatementResponseLine(july_7_2021, Money.of(-50, CURRENCY), Money.of(50, CURRENCY))
                )
        );
    }

    @Override
    public void accept(PrepareStatementResponse response) {
        this.response = response;
    }
}