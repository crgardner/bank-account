package com.crg.learn.usecase.account.adjust;

import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.account.shared.AccountResponseBuilder;
import com.crg.learn.usecase.concept.UseCase;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.util.Optional;

class AdjustAccount implements UseCase<AdjustAccountRequest, AdjustAccountResponder> {

    private final Bank bank;

    public AdjustAccount(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void execute(AdjustAccountRequest request, AdjustAccountResponder responder) {
        var possibleAccount = findAccount(request);

        possibleAccount.ifPresentOrElse(account -> handle(account, request, responder), responder::onNotFound);
    }

    private void handle(Account account, AdjustAccountRequest request, AdjustAccountResponder responder) {
        adjust(request, account);
        update(account);
        respond(responder, account);
    }

    private void update(Account account) {
        bank.update(account);
    }

    private void respond(AdjustAccountResponder responder, Account account) {
        var builder = new AccountResponseBuilder();
        account.writeTo(builder);

        responder.accept(builder.build());
    }

    private void adjust(AdjustAccountRequest request, Account account) {
        account.add(new Entry(Money.of(request.amount(), Monetary.getCurrency(request.currency()))));
    }

    private Optional<Account> findAccount(AdjustAccountRequest request) {
        var accountNumber = new AccountNumber(request.accountNumber());

        return bank.lookup(accountNumber);
    }
}