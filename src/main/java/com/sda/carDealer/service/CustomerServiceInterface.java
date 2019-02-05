package com.sda.carDealer.service;

import com.sda.carDealer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerServiceInterface {
    Customer addNewCustomer(Customer customer);
    List<Customer> getAll();
    Page<Customer> getAllPaginated(Pageable pageable);
    Optional<Customer> getById(Long id);
}
