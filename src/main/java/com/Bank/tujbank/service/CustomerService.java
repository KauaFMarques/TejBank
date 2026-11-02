package com.Bank.tujbank.service;

import com.Bank.tujbank.dto.CustomerRequestDTO;
import com.Bank.tujbank.dto.CustomerResponseDTO;
import com.Bank.tujbank.model.Customer;
import com.Bank.tujbank.repository.CustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service 
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto){
        if(customerRepository.findByEmail(dto.email()).isPresent()){
            throw new IllegalArgumentException("Este email já está cadastrado.");
        }

        if(customerRepository.findByCpf(dto.cpf()).isPresent()){
            throw new IllegalArgumentException("Este cpf já está cadastrado.");
        }
        Customer customer = new Customer();

        customer.setCpf(dto.cpf());
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPassword(dto.password());

        Customer salvo = customerRepository.save(customer);

        return new CustomerResponseDTO(
                salvo.getId(),
                salvo.getName(),
                salvo.getEmail(),
                salvo.getCpf(),
                salvo.getDataCreation()
        );
    }
}
