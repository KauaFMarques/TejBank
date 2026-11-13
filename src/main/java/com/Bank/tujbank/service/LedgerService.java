package com.Bank.tujbank.service;

import com.Bank.tujbank.enums.EntryType;
import com.Bank.tujbank.enums.TransactionType;
import com.Bank.tujbank.exception.InsuficienceBalanceException;
import com.Bank.tujbank.model.Account;
import com.Bank.tujbank.model.LedgerEntry;
import com.Bank.tujbank.model.Transaction;
import com.Bank.tujbank.repository.AccountRepository;
import com.Bank.tujbank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class LedgerService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public LedgerService(AccountRepository accountRepository,
                         TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction deposit(Account account, BigDecimal amount, String description) {
        if (amount.signum() <= 0) throw new IllegalArgumentException("Valor deve ser positivo");

        Transaction tx = new Transaction();
        tx.setType(TransactionType.DEPOSIT);
        tx.setAmount(amount);
        tx.setDescription(description);

        LedgerEntry entry = new LedgerEntry();
        entry.setTransaction(tx);
        entry.setAccount(account);
        entry.setEntryType(EntryType.CREDIT);
        entry.setAmount(amount);
        tx.getEntries().add(entry);

        account.setBalance(account.getBalance().add(amount));   // saldo materializado
        accountRepository.save(account);
        return transactionRepository.save(tx);
    }

    @Transactional
    public Transaction withdraw(Account account, BigDecimal amount, String description) {
        if (amount.signum() <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        if (account.getBalance().compareTo(amount) < 0)
            throw new InsuficienceBalanceException(account.getId(), account.getBalance(), amount);

        Transaction tx = new Transaction();
        tx.setType(TransactionType.WITHDRAWAL);
        tx.setAmount(amount);
        tx.setDescription(description);

        LedgerEntry entry = new LedgerEntry();
        entry.setTransaction(tx);
        entry.setAccount(account);
        entry.setEntryType(EntryType.DEBIT);
        entry.setAmount(amount);
        tx.getEntries().add(entry);

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        return transactionRepository.save(tx);
    }

    @Transactional
    public Transaction transfer(Account from, Account to, BigDecimal amount, String description) {
        if (amount.signum() <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        if (from.getBalance().compareTo(amount) < 0)
            throw new InsuficienceBalanceException(from.getId(), from.getBalance(), amount);

        Transaction tx = new Transaction();
        tx.setType(TransactionType.TRANSFER);
        tx.setAmount(amount);
        tx.setDescription(description);

        LedgerEntry debit = new LedgerEntry();
        debit.setTransaction(tx);
        debit.setAccount(from);
        debit.setEntryType(EntryType.DEBIT);
        debit.setAmount(amount);

        LedgerEntry credit = new LedgerEntry();
        credit.setTransaction(tx);
        credit.setAccount(to);
        credit.setEntryType(EntryType.CREDIT);
        credit.setAmount(amount);

        tx.getEntries().add(debit);
        tx.getEntries().add(credit);

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);

        return transactionRepository.save(tx);
    }
}
