package com.crg.learn.controller.account.open;

import com.crg.learn.usecase.account.open.*;
import com.crg.learn.usecase.shared.AccountResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.function.*;

import static com.crg.learn.controller.test.support.MonetaryAmounts.*;
import static com.crg.learn.controller.test.support.UseCaseMocking.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OpenAccountController.class)
@DisplayName("OpenAccountController")
class OpenAccountControllerTest {
    private static final String CLIENT_URI = "/banking/v1/accounts";

    @Autowired
    @SuppressWarnings("all")
    private MockMvc mockMvc;

    @MockBean
    @SuppressWarnings("all")
    private OpenAccountUseCase useCase;

    @Test
    @DisplayName("controls request to open accounts")
    void controlsRequestToOpenAccounts() throws Exception {
        prepare(useCase, toProvideNewlyOpenedAccount());

        mockMvc.perform(post(CLIENT_URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(openAccountPostBody())
                            .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().is(201))
               .andExpect(content().json(expectedAccountResource()));
    }

    private Consumer<OpenAccountResponder> toProvideNewlyOpenedAccount() {
        return responder -> responder.accept(new AccountResponse("123", "Ford", "Prefect", euros_0()));
    }

    private String openAccountPostBody() {
        return """
                 {
                    "firstName": "Ford",
                    "lastName": "Prefect"
                 }
                """;
    }

    private String expectedAccountResource() {
        return """
                {
                    "accountNumber": "123",
                    "firstName": "Ford",
                    "lastName": "Prefect",
                    "balance": "0.00";
                    "currency": "EUR"
                }
                """;
    }

}