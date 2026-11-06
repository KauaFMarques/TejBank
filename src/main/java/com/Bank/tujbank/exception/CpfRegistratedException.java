package com.Bank.tujbank.exception;

public class CpfRegistratedException extends RuntimeException {
    public CpfRegistratedException(String cpf) {
        super("CPF já cadastrado: " + cpf);
    }
}
