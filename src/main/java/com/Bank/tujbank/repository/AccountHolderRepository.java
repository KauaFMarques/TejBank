package com.Bank.tujbank.repository;

import com.Bank.tujbank.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
    List<AccountHolder> findByAccountId(Long accountId);
    List<AccountHolder> findByCustomerId(Long customerId);
}