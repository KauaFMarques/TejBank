package com.Bank.tujbank.dto;

import com.Bank.tujbank.enums.AccountType;

import java.math.BigDecimal;

public record AccountResponseDTO(
        Long id,
        String accountNumber,
        String agency,
        BigDecimal balance,
        AccountType type,
        String holderName     // ← 6º campo
) {}
