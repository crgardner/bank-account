package com.crg.learn.usecase.account.deposit;

import com.crg.learn.usecase.account.shared.AccountResponse;
import org.javamoney.moneta.Money;

public interface AdjustAccountResponder {
    void accept(AccountResponse response);

    void onNotFound();
}

