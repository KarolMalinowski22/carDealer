package com.sda.carDealer.service;

import com.sda.carDealer.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerServiceInterface {
    Customer addNewCustomer(Customer customer);
    List<Customer> getAll();

    Optional<Customer> getById(Long id);
}
