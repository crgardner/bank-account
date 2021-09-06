package com.crg.learn.persistence.account;

import static org.assertj.core.api.Assertions.*;
import com.crg.learn.domain.account.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.mockito.Mockito.*;

@DisplayName("AccountGateway")
@ExtendWith(MockitoExtension.class)
class AccountGatewayTest {

    @Mock
    private AccountJpaRepository repository;

    private AccountGateway adapter;
    private Account account;
    private PersistentAccount persistentAccount;

    @BeforeEach
    void init() {
        adapter = new AccountGateway(repository);
        account = make(an(Account));
        persistentAccount = new PersistentAccountMapper(account).map();
    }

    @Test
    @DisplayName("saves account")
    void savesAccount() {
        adapter.open(account);

        verify(repository).save(any(PersistentAccount.class));
    }

    @Test
    @DisplayName("finds account by account number")
    void findsAccount() {
        when(repository.findByAccountNumber("123")).thenReturn(Optional.of(persistentAccount));

        var possibleAccount = adapter.lookup(new AccountNumber("123"));

        assertThat(possibleAccount).contains(account);
    }
}