package com.sda.carDealer.repository;

import com.sda.carDealer.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
    List<Operator> findAllByVisibleTrue();
}
