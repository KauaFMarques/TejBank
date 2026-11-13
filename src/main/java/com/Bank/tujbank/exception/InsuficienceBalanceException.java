package com.Bank.tujbank.exception;


import java.math.BigDecimal;

public class InsuficienceBalanceException extends RuntimeException {

    public InsuficienceBalanceException(Long accountId, BigDecimal saldo, BigDecimal solicitado) {
        super("Saldo insuficiente na conta " + accountId
                + ": saldo atual = " + saldo
                + ", valor solicitado = " + solicitado);
    }
}