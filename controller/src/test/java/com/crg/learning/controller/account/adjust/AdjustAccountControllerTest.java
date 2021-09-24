package com.crg.learning.controller.account.adjust;

import com.crg.learn.usecase.account.adjust.*;
import com.crg.learn.usecase.shared.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.money.Monetary;
import java.time.Instant;
import java.util.Collections;
import java.util.function.*;

import static com.crg.learning.controller.test.support.UseCaseMocking.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdjustAccountController.class)
@DisplayName("AdjustAccountController")
class AdjustAccountControllerTest {
    private static final String CLIENT_URI = "/banking/v1/accounts/123/adjustments";

    @Autowired
    @SuppressWarnings("all")
    private MockMvc mockMvc;

    @MockBean
    @SuppressWarnings("all")
    private AdjustAccountUseCase useCase;
    private Instant now;

    @BeforeEach
    void init() {
        now = Instant.now();
    }

    @Test
    @DisplayName("controls request to adjust accounts")
    void controlsRequestToAdjustAccounts() throws Exception {
        prepare(useCase, toProvideCurrentAccountBalance());

        mockMvc.perform(post(CLIENT_URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(adjustAccountPostBody())
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(content().json(expectedAccountResource()));
    }

    private String adjustAccountPostBody() {
        return """
                {
                    "amount": "100.00",
                    "currency": "EUR"
                }
                """;
    }

    private String expectedAccountResource() {
        return """
                {
                    "accountNumber": "123",
                    "balance": "100.00",
                    "currency": "EUR",
                    "transactionId": "abc",
                    "amount": "100.00"
                }
                """;
    }

    private Consumer<AdjustAccountResponder> toProvideCurrentAccountBalance() {
        var entries = Collections.singletonList(new EntryResponse("abc", now, Money.of(100, Monetary.getCurrency("EUR"))));
        return (responder) ->
                responder.accept(new AccountResponse("123","Ford", "Prefect", Money.of(100, Monetary.getCurrency("EUR")), entries));
    }

    @Test
    @DisplayName("reports account not found")
    void reportsAccountNotFound() throws Exception {
        prepare(useCase, toReportAccountNotFound());

        mockMvc.perform(post(CLIENT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adjustAccountPostBody())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    private Consumer<AdjustAccountResponder> toReportAccountNotFound() {
        return AdjustAccountResponder::onNotFound;
    }

}
