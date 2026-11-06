package com.Bank.tujbank.validation;

import java.util.InputMismatchException;

public class CpfAvaliable {

    public static boolean isCPF(String CPF) {
        if (CPF == null) return false;

        // remove máscara, se houver
        CPF = CPF.replaceAll("[^0-9]", "");

        if (CPF.length() != 11) return false;

        // rejeita sequências iguais
        if (CPF.matches("(\\d)\\1{10}")) return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            dig10 = (r == 10 || r == 11) ? '0' : (char)(r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            dig11 = (r == 10 || r == 11) ? '0' : (char)(r + 48);

            return dig10 == CPF.charAt(9) && dig11 == CPF.charAt(10);
        } catch (Exception e) {
            return false;
        }
    }
}