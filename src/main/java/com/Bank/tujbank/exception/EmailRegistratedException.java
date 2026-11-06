package com.Bank.tujbank.exception;

public class EmailRegistratedException extends RuntimeException{
    public EmailRegistratedException(String email) {
        super("Email já cadastrado: " + email);
    }

}
