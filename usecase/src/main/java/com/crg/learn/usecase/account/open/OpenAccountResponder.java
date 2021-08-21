package com.crg.learn.usecase.account.open;

import com.crg.learn.usecase.account.shared.AccountResponse;

public interface OpenAccountResponder {
    void accept(AccountResponse response);
}
