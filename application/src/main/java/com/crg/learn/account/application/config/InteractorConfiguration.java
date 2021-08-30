package com.crg.learn.account.application.config;

import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.account.open.*;
import org.springframework.context.annotation.*;

@Configuration
public class InteractorConfiguration {

    @Bean
    OpenAccountUseCase openAccountUseCase(Bank bank) {
        return new OpenAccountInteractor(bank, new UUIDAccountNumberProvider());
    }

}
