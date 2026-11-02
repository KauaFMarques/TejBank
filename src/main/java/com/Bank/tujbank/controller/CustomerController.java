package com.Bank.tujbank.controller;

import com.Bank.tujbank.dto.CustomerRequestDTO;
import com.Bank.tujbank.dto.CustomerResponseDTO;
import com.Bank.tujbank.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponseDTO> register(@RequestBody @Valid CustomerRequestDTO dto) {
        CustomerResponseDTO newCustomer = customerService.createCustomer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }
}


