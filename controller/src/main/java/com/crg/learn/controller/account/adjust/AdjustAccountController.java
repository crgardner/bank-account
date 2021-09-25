package com.crg.learn.controller.account.adjust;

import com.crg.learn.controller.http.ResponseEntityResponseWriter;
import com.crg.learn.presenter.account.adjust.AdjustAccountPresenter;
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
        var responseWriter = new ResponseEntityResponseWriter();
        var presenter = new AdjustAccountPresenter(responseWriter);

        useCase.execute(request, presenter);

        return responseWriter.entity();
    }

    private AdjustAccountRequest requestFrom(String accountId, AccountAdjustmentResource resource) {
        return new AdjustAccountRequest(accountId, Double.parseDouble(resource.amount()), resource.currency());
    }

}
