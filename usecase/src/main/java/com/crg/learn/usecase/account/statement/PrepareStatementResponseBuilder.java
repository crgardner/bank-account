package com.crg.learn.usecase.account.statement;

import com.crg.learn.domain.account.AccountStatementReader;
import org.javamoney.moneta.Money;

import java.time.Instant;
import java.util.*;

class PrepareStatementResponseBuilder implements AccountStatementReader {
    private final List<PrepareStatementResponseLine> lines = new ArrayList<>();

    @Override
    public void addLine(Instant whenBooked, Money amount, Money balance) {
        lines.add(new PrepareStatementResponseLine(whenBooked, amount, balance));
    }

    PrepareStatementResponse build() {
        return new PrepareStatementResponse(Collections.unmodifiableList(lines));
    }

}
