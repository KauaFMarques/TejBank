package com.Bank.tujbank.dto;

import com.Bank.tujbank.enums.AccountType;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDTO (
        @NotNull(message = "O ID do cliente é obrigatório")
        Long customerId,

        @NotNull(message = "O tipo de conta é obrigatório")
        AccountType type
) {}