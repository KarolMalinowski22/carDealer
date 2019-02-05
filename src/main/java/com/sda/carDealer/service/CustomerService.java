package com.sda.carDealer.service;

import com.sda.carDealer.model.Customer;
import com.sda.carDealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements CustomerServiceInterface {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addNewCustomer(Customer customer) {
        for (Customer c : getAll()) {
            if (c.equals(customer)) {
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
    public Page<Customer> getAllPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        List<Customer> allCustomers = getAll();
        List<Customer> customersListPage;
        int startIndex = pageNumber * pageSize;
        if (startIndex > allCustomers.size()) {
            customersListPage = Collections.emptyList();
        }else{
            customersListPage = allCustomers.subList(startIndex, Math.min(startIndex + pageSize, allCustomers.size()));
        }
        return new PageImpl<Customer>(customersListPage, PageRequest.of(pageNumber, pageSize), allCustomers.size());
    }

    @Override
    public Optional<Customer> getById(Long id) {
        return customerRepository.findById(id);
    }
}
