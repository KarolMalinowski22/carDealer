package com.sda.carDealer.service;

import com.sda.carDealer.model.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerServiceInterface {
    Operator addNewCustomer(Operator operator);
    List<Operator> getAll();
    Page<Operator> getAllPaginated(Pageable pageable);
    Optional<Operator> getById(Long id);
}
