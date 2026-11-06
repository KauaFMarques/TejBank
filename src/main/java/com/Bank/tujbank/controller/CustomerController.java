package com.Bank.tujbank.controller;

import com.Bank.tujbank.dto.CustomerRequestDTO;
import com.Bank.tujbank.dto.CustomerResponseDTO;
import com.Bank.tujbank.model.Customer;
import com.Bank.tujbank.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Customer> getAllProducts() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/paged")
    public Iterable<Customer> getAllProductsPaged(@RequestParam int page, @RequestParam int size) {
        return customerService.findAllCustomersPagedAndSorted(page, size);
    }

    @PatchMapping("/update/{id}/{customerName}")
    public Customer updateCustomerWithId(@PathVariable("id") Long id, @PathVariable("customerName") String customerName){
        return customerService.updateCustomerWithId(id, customerName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}


