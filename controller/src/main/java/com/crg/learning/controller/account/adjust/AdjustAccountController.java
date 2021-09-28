package com.crg.learning.controller.account.adjust;

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
                                                @RequestBody AccountAdjustmentResource resource) {
        var request = requestFrom(accountId, resource);
        var presenter = new AdjustAccountPresenter();

        useCase.execute(request, presenter);

        return presenter.responseEntity();
    }

    private AdjustAccountRequest requestFrom(String accountId, AccountAdjustmentResource resource) {
        return new AdjustAccountRequest(accountId, Double.parseDouble(resource.amount()), resource.currency());
    }

}
