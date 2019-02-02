package com.sda.carDealer.service;

import com.sda.carDealer.model.Customer;
import com.sda.carDealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerServiceInterface {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void addNewCustomer(Customer customer){
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
