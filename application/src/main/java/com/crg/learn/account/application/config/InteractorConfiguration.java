package com.crg.learn.account.application.config;

import com.crg.learn.domain.account.*;
import com.crg.learn.interactor.account.open.OpenAccountInteractor;
import com.crg.learn.usecase.account.open.OpenAccountUseCase;
import org.springframework.context.annotation.*;

@Configuration
public class InteractorConfiguration {

    @Bean
    OpenAccountUseCase openAccountUseCase(AccountRepository accountRepository) {
        return new OpenAccountInteractor(accountRepository, new UUIDAccountNumberProvider());
    }

}
