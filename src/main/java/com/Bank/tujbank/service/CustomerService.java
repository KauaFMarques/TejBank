package com.Bank.tujbank.service;

import com.Bank.tujbank.dto.CustomerRequestDTO;
import com.Bank.tujbank.dto.CustomerResponseDTO;
import com.Bank.tujbank.exception.CpfRegistratedException;
import com.Bank.tujbank.exception.CustomerNotFoundException;
import com.Bank.tujbank.exception.EmailRegistratedException;
import com.Bank.tujbank.model.Customer;
import com.Bank.tujbank.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {
        if (customerRepository.findByEmail(dto.email()).isPresent()) {
            throw new EmailRegistratedException(dto.email());
        }
        if (customerRepository.findByCpf(dto.cpf()).isPresent()) {
            throw new CpfRegistratedException(dto.cpf());
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

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Iterable<Customer> findAllCustomersPagedAndSorted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return customerRepository.findAll(pageable);
    }

    @Transactional
    public Customer updateCustomerWithId(Long id, String name) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setName(name);
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setDeletedAt(java.time.LocalDateTime.now());
        customerRepository.delete(customer);
    }
}