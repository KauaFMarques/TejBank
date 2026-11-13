package com.Bank.tujbank.service;


import com.Bank.tujbank.dto.AccountRequestDTO;
import com.Bank.tujbank.dto.AccountResponseDTO;
import com.Bank.tujbank.enums.HolderType;
import com.Bank.tujbank.enums.MandateType;
import com.Bank.tujbank.exception.CustomerNotFoundException;
import com.Bank.tujbank.model.Account;
import com.Bank.tujbank.model.AccountHolder;
import com.Bank.tujbank.model.Customer;
import com.Bank.tujbank.repository.AccountRepository;
import com.Bank.tujbank.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;

    public AccountService(CustomerRepository customerRepository,
                          AccountRepository accountRepository,
                          AccountNumberGenerator accountNumberGenerator) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO dto) {
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.customerId()));

        Account account = new Account();
        account.setAccountNumber(accountNumberGenerator.generate());
        account.setType(dto.type());

        AccountHolder holder = new AccountHolder();
        holder.setAccount(account);
        holder.setCustomer(customer);
        holder.setHolderType(HolderType.PRIMARY);
        holder.setMandateType(MandateType.OR);

        account.getHolders().add(holder);
        Account saved = accountRepository.save(account);
        return toDto(saved, holder);   // ✅
    }

    private AccountResponseDTO toDto(Account account, AccountHolder primaryHolder) {
        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getAgency(),
                account.getBalance(),
                account.getType(),
                primaryHolder.getCustomer().getName()   // 6º argumento
        );
    }
}