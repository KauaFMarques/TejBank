package com.Bank.tujbank.service;

import com.Bank.tujbank.enums.AccountType;

public class AccountRules {
    // Decisão: regras por tipo ficam em um service, não em herança.
    // Motivo: herança JPA (SINGLE_TABLE/JOINED) complica o schema e não traz ganho
    // para o escopo atual. Strategy via if/switch é suficiente e evolui fácil.

    public static boolean permiteChequeEspecial(AccountType type) {
        return type == AccountType.CHECKING;   // TODO: implementar limite
    }

    public static boolean rende(AccountType type) {
        return type == AccountType.SAVINGS;    // TODO: implementar taxa
    }
}