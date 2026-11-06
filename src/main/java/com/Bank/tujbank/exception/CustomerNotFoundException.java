package com.Bank.tujbank.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Cliente não encontrado com id: " + id);
    }
}
