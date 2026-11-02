package com.Bank.tujbank.enums;

import com.Bank.tujbank.validation.AccountType;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDTO (
        @NotNull(message = "O ID do cliente é obrigatório")
        Long customerId,

        @NotNull(message = "O tipo de conta é obrigatório")
        AccountType type
) {}
