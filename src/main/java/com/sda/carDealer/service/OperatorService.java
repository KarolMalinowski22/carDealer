package com.sda.carDealer.service;

import com.sda.carDealer.model.Operator;
import com.sda.carDealer.repository.OperatorRepository;
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
public class OperatorService implements OperatorServiceInterface {
    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    public Operator addNewCustomer(Operator operator) {
        for (Operator c : getAll()) {
            if (c.equals(operator)) {
                return c;
            }
        }
        operatorRepository.save(operator);
        return operator;
    }

    @Override
    public List<Operator> getAll() {
        return operatorRepository.findAll();
    }

    @Override
    public Page<Operator> getAllPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        List<Operator> allOperators = getAll();
        List<Operator> customersListPage;
        int startIndex = pageNumber * pageSize;
        if (startIndex > allOperators.size()) {
            customersListPage = Collections.emptyList();
        }else{
            customersListPage = allOperators.subList(startIndex, Math.min(startIndex + pageSize, allOperators.size()));
        }
        return new PageImpl<>(customersListPage, PageRequest.of(pageNumber, pageSize), allOperators.size());
    }

    @Override
    public Optional<Operator> getById(Long id) {
        return operatorRepository.findById(id);
    }
}
