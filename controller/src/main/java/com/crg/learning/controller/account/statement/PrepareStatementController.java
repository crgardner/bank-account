package com.crg.learning.controller.account.statement;

import com.crg.learn.usecase.account.statement.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@RestController
public class PrepareStatementController {
    private final PrepareAccountStatementUseCase useCase;

    public PrepareStatementController(PrepareAccountStatementUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/banking/v1/accounts/{account-id}/statement", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> prepareStatement(@PathVariable("account-id") String accountId) {
        var presenter = new PrepareAccountStatementPresenter();
        useCase.execute(new PrepareAccountStatementRequest(accountId), presenter);

        return presenter.responseEntity();
    }
}
