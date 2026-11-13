package com.Bank.tujbank.model;

import com.Bank.tujbank.enums.AccountType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String accountNumber;

    @Column(nullable = false, length = 4)
    private String agency = "0001"; // Agência padrão para começar

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO; // Começa sempre com saldo zero

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountHolder> holders = new ArrayList<>();

    public List<AccountHolder> getHolders() {
        return holders;
    }

    public void setHolders(List<AccountHolder> holders) {
        this.holders = holders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void credit(BigDecimal value) {
        this.balance = this.balance.add(value);
    }

    public void debit(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }
}
