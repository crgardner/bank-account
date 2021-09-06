package com.crg.learn.account.application.config;

import com.crg.learn.domain.account.AccountRepository;
import com.crg.learn.persistence.account.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.crg.learn.persistence")
@EntityScan(basePackages = "com.crg.learn.persistence")
public class PersistenceConfiguration {

    @Bean
    AccountRepository accountGatewayAdapter(AccountJpaRepository repository) {
        return new AccountGateway(repository);
    }
}
