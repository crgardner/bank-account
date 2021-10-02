package com.crg.learn.controller.account.statement;

import com.crg.learn.usecase.account.statement.*;
import com.crg.learn.controller.account.shared.BasicMoneyFormatter;
import org.springframework.http.ResponseEntity;

import static java.util.stream.Collectors.*;

public class PrepareAccountStatementPresenter implements PrepareAccountStatementResponder {
    private ResponseEntity<Object> entity;

    @Override
    public void accept(PrepareStatementResponse response) {
        var resource = response.lines().stream().map(this::toLine).collect(collectingAndThen(toList(), StatementViewModel::new));
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
        entity = ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> responseEntity() {
        return entity;
    }
}