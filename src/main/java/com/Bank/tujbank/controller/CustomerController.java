package com.Bank.tujbank.controller;

import com.Bank.tujbank.dto.CustomerRequestDTO;
import com.Bank.tujbank.dto.CustomerResponseDTO;
import com.Bank.tujbank.service.CustomerService;
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

    @GetMapping
    public List<CustomerResponse> getAll(@RequestParam(name = "name", required = false) String name) {
        return customerService.getAll(name).stream()
                .map(CustomerExtension::toResponse)
                .toList();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponseDTO> register(@RequestBody @Valid CustomerRequestDTO dto) {
        CustomerResponseDTO newCustomer = customerService.createCustomer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.id")
    public CustomerResponse getCustomer(@PathVariable("id") Integer id) {
        return CustomerExtension.toResponse(customerService.findById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer id, @RequestBody @Valid PutCustomerRequest customer) {
        CustomerModel customerSaved = customerService.findById(id);
        customerService.update(CustomerExtension.toCustomerModel(customer, customerSaved));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        customerService.delete(id);
    }
}
}
