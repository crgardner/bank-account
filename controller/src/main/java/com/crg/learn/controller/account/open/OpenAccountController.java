package com.crg.learn.controller.account.open;

import com.crg.learn.usecase.account.open.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@RestController
public class OpenAccountController {
    private final OpenAccountUseCase useCase;

    public OpenAccountController(OpenAccountUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping(value = "/banking/v1/accounts", consumes = APPLICATION_JSON_VALUE,
                                                 produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> openAccount(@RequestBody OpenAccountDetails details) {
        var request = requestFrom(details);
        var presenter = new OpenAccountPresenter();

        useCase.execute(request, presenter);

        return presenter.responseEntity();
    }

    private OpenAccountRequest requestFrom(OpenAccountDetails details) {
        return new OpenAccountRequest(details.firstName(), details.lastName());
    }
}
