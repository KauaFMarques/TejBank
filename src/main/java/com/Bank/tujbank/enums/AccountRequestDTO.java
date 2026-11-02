package com.Bank.tujbank.enums;

import org.antlr.v4.runtime.misc.NotNull;

public record AccountRequestDTO (
        @NotNull(message = "O ID do cliente é obrigatório")
        Long customerId,

        @NotNull(message = "O tipo de conta é obrigatório")
        TipoConta type
) {}
