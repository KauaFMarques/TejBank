package com.Bank.tujbank.service;

import com.Bank.tujbank.dto.CustomerRequestDTO;
import com.Bank.tujbank.dto.CustomerResponseDTO;
import com.Bank.tujbank.model.Customer;
import com.Bank.tujbank.repository.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustumerResponseDTO createCustomer(CustomerRequestDTO dto){
        if(customerRepository.findByEmail(dto.email()).isPresent()){
            throw new IllegalArgumentException("Este email já está cadastrado.");
        }

        if(customerRepository.findByCpf(dto.cpf()).isPresent()){
            throw new IllegalArgumentException("Este cpf já está cadastrado.");
        }
        Customer customer = new Customer();

        Customer salvo = customerRepository.save();

        return new CustomerResponseDTO(salvo.getId(), salvo.getCpf(), salvo.getName(), salvo.getEmail(), salvo.getPassword(), salvo.getDataCreation());
    }
}
