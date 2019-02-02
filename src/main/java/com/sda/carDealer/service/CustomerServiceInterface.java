package com.sda.carDealer.service;

import com.sda.carDealer.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CustomerServiceInterface {
    void addNewCustomer(Customer customer);
    List<Customer> getAll();
}
