package com.Bank.tujbank.dto;

import java.time.LocalDateTime;

public record CustomerResponseDTO(
        Long id,
        String nome,
        String email,
        LocalDateTime dataCriacao
) {}
