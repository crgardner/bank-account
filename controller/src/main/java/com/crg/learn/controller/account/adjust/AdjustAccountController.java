package com.crg.learn.controller.account.adjust;

import com.crg.learn.usecase.account.adjust.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@RestController
public class AdjustAccountController {
    private final AdjustAccountUseCase useCase;

    public AdjustAccountController(AdjustAccountUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping(value = "/banking/v1/accounts/{account-id}/adjustments", consumes = APPLICATION_JSON_VALUE,
                                                                          produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> adjustAccount(@PathVariable ("account-id") String accountId,
                                                @RequestBody AdjustAccountDetails details) {
        var request = requestFrom(accountId, details);
        var presenter = new AdjustAccountPresenter();

        useCase.execute(request, presenter);

        return presenter.responseEntity();
    }

    private AdjustAccountRequest requestFrom(String accountId, AdjustAccountDetails details) {
        return new AdjustAccountRequest(accountId, Double.parseDouble(details.amount()), details.currency());
    }

}