package com.sda.carDealer.service;

import com.sda.carDealer.model.Customer;
import com.sda.carDealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements CustomerServiceInterface {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addNewCustomer(Customer customer){
        for(Customer c : getAll()){
            if(c.equals(customer)){
                return c;
            }
        }
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getById(Long id) {
            return customerRepository.findById(id);
    }
}
