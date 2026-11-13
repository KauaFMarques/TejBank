package com.Bank.tujbank.service;

import com.Bank.tujbank.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountNumberGenerator {

    private final AccountRepository accountRepository;

    public AccountNumberGenerator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Gera número de conta no formato: 8 dígitos de sequência + 2 dígitos verificadores (módulo 11).
     * Ex.: 00000001-09  →  "0000000109"
     */
    public String generate() {
        long next = accountRepository.count() + 1;          // sequência simples
        String base = String.format("%08d", next);          // 8 dígitos com zeros à esquerda
        String dv = calculateMod11Dv(base);                // 2 dígitos verificadores
        return base + dv;                                  // total: 10 caracteres
    }

    /**
     * Módulo 11 padrão bancário:
     * - Pesos 2,3,4,...,9,2,3,... da direita para a esquerda
     * - Soma dos produtos
     * - resto = soma % 11
     * - dv = 11 - resto; se dv == 10 ou 11 → 0; se dv == 0 → 0
     */
    private String calculateMod11Dv(String base) {
        int[] pesos = {2, 3, 4, 5, 6, 7, 8, 9};
        int soma = 0;
        int pesoIdx = 0;
        for (int i = base.length() - 1; i >= 0; i--) {
            int digito = base.charAt(i) - '0';
            soma += digito * pesos[pesoIdx % pesos.length];
            pesoIdx++;
        }
        int resto = soma % 11;
        int dv = 11 - resto;
        if (dv == 10 || dv == 11) dv = 0;
        return String.format("%02d", dv);  // 2 dígitos
    }
}
