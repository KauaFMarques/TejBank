package com.Bank.tujbank.controller;

import com.Bank.tujbank.dto.CustomerRequestDTO;
import com.Bank.tujbank.dto.CustomerResponseDTO;
import com.Bank.tujbank.enums.AuthRequestDTO;
import com.Bank.tujbank.model.Customer;
import com.Bank.tujbank.service.CustomerService;
import com.Bank.tujbank.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final UserDetailsService userDetailsService;
        private final JwtService jwtService;

        public AuthController(AuthenticationManager authenticationManager,
                              UserDetailsService userDetailsService,
                              JwtService jwtService) {
            this.authenticationManager = authenticationManager;
            this.userDetailsService = userDetailsService;
            this.jwtService = jwtService;
        }

        @PostMapping("/login")
        public Map<String, String> login(@RequestBody AuthRequestDTO dto) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
            UserDetails user = userDetailsService.loadUserByUsername(dto.email());
            String token = jwtService.generateToken(user);
            return Map.of("token", token);
        }
    }

}


