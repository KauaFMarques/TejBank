package com.Bank.tujbank.enums;

import com.Bank.tujbank.validation.AccountType;

import java.math.BigDecimal;

public record AccountResponseDTO(
        Long id,
        String accountName,
        String agency,
        BigDecimal balance,
        AccountType type,
        String customerName
) {}
