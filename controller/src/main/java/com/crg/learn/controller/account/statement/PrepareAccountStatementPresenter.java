package com.crg.learn.controller.account.statement;

import com.crg.learn.controller.presenter.BasePresenter;
import com.crg.learn.usecase.account.statement.*;
import com.crg.learn.controller.account.shared.BasicMoneyFormatter;
import org.springframework.http.ResponseEntity;

import static java.util.stream.Collectors.*;

public class PrepareAccountStatementPresenter extends BasePresenter implements PrepareAccountStatementResponder {

    @Override
    public void accept(PrepareStatementResponse response) {
        var viewModel = response.lines().stream().map(this::toLine)
                                                 .collect(collectingAndThen(toList(), StatementViewModel::new));
        responseOf(ResponseEntity.ok(viewModel));
    }

    private Line toLine(PrepareStatementResponseLine responseLine) {
        return new Line(responseLine.whenBooked().toString(),
                        creditOrDebit(responseLine),
                        BasicMoneyFormatter.formatAbs(responseLine.amount()),
                        BasicMoneyFormatter.format(responseLine.balance())
        );
    }

    private String creditOrDebit(PrepareStatementResponseLine responseLine) {
        return responseLine.amount().isNegative() ? "DEBIT" : "CREDIT";
    }


}
