package com.crg.learn.account.application.config;

import com.crg.learn.usecase.account.adjust.AdjustAccountUseCase;
import com.crg.learn.usecase.account.open.OpenAccountUseCase;
import com.crg.learning.controller.account.adjust.AdjustAccountController;
import com.crg.learning.controller.account.open.OpenAccountController;
import org.springframework.context.annotation.*;

@Configuration
public class ControllerConfiguration {

    @Bean
    OpenAccountController openAccountController(OpenAccountUseCase useCase) {
        return new OpenAccountController(useCase);
    }

    @Bean
    AdjustAccountController adjustAccountController(AdjustAccountUseCase useCase) {
        return new AdjustAccountController(useCase);
    }
}
