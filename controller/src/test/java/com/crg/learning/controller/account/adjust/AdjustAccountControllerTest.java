package com.crg.learning.controller.account.adjust;

import com.crg.learn.usecase.account.adjust.*;
import com.crg.learn.usecase.shared.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.function.Consumer;

import static com.crg.learning.controller.test.support.BookingDates.*;
import static com.crg.learning.controller.test.support.MonetaryAmounts.*;
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
        var entries = Collections.singletonList(new EntryResponse("abc", jun_21_2021(), euros_100()));

        return responder -> responder.accept(new AccountResponse("123", "Ford", "Prefect", euros_100(), entries));
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
