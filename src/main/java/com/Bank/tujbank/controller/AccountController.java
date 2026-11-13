package com.Bank.tujbank.controller;

import com.Bank.tujbank.dto.AccountRequestDTO;
import com.Bank.tujbank.dto.AccountResponseDTO;
import com.Bank.tujbank.model.AccountHolder;
import com.Bank.tujbank.repository.AccountHolderRepository;
import com.Bank.tujbank.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class AccountController {

    private final AccountService accountService;
    private final AccountHolderRepository accountHolderRepository;

    public AccountController(AccountService accountService,
                             AccountHolderRepository accountHolderRepository) {
        this.accountService = accountService;
        this.accountHolderRepository = accountHolderRepository;
    }

    @PostMapping
    public AccountResponseDTO create(@RequestBody AccountRequestDTO dto) {
        return accountService.createAccount(dto);
    }

    @GetMapping("/{accountId}/holders")
    public List<AccountHolder> getHolders(@PathVariable Long accountId) {
        return accountHolderRepository.findByAccountId(accountId);
    }
}