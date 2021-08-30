package com.crg.learn.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<PersistentAccount, Long> {
    Optional<PersistentAccount> findByAccountNumber(String accountNumber);
}
