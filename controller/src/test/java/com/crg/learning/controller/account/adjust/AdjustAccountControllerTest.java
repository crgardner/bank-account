package com.crg.learning.controller.account.adjust;

import com.crg.learn.usecase.account.adjust.*;
import com.crg.learn.usecase.shared.AccountResponse;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.money.Monetary;
import java.util.function.BiConsumer;

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

    @Test
    @DisplayName("controls request to adjust accounts")
    void controlsRequestToAdjustAccounts() throws Exception {
        prepare(useCase, toProvideCurrentAccountBalance());

        mockMvc.perform(post(CLIENT_URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(adjustAccountPostBody())
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
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
                    "firstName": "Ford",
                    "lastName": "Prefect",
                    "balance": "100.00";
                    "currency": "EUR"
                }
                """;
    }

    private BiConsumer<AdjustAccountRequest, AdjustAccountResponder> toProvideCurrentAccountBalance() {
        return (request, responder) ->
                responder.accept(new AccountResponse("123", "Ford", "Prefect", Money.of(100, Monetary.getCurrency("EUR"))));
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

    private BiConsumer<AdjustAccountRequest, AdjustAccountResponder> toReportAccountNotFound() {
        return (request, responder) -> responder.onNotFound();
    }
}
