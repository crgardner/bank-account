package com.crg.learning.controller.account.statement;

import com.crg.learn.usecase.account.statement.*;
import com.crg.learning.controller.account.shared.BasicMoneyFormatter;
import org.springframework.http.ResponseEntity;

import static java.util.stream.Collectors.*;

public class PrepareAccountStatementPresenter implements PrepareAccountStatementResponder {
    private ResponseEntity<Object> entity;

    @Override
    public void accept(PrepareStatementResponse response) {
        var resource = response.lines().stream().map(this::toLine).collect(collectingAndThen(toList(), Statement::new));
        entity = ResponseEntity.ok(resource);
    }

    private Line toLine(PrepareStatementResponseLine responseLine) {

        return new Line(responseLine.whenBooked().toString(),
                        creditOrDebit(responseLine),
                        BasicMoneyFormatter.formatAbs(responseLine.amount()),
                        BasicMoneyFormatter.formatAbs(responseLine.balance())
        );
    }

    private String creditOrDebit(PrepareStatementResponseLine responseLine) {
        return responseLine.amount().isNegative() ? "DEBIT" : "CREDIT";
    }

    @Override
    public void onNotFound() {

    }

    public ResponseEntity<Object> responseEntity() {
        return entity;
    }
}
