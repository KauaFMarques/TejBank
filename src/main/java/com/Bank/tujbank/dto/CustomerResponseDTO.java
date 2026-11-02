package com.Bank.tujbank.dto;

import java.time.LocalDateTime;

public record CustomerResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        LocalDateTime dataCriacao
) {

}
