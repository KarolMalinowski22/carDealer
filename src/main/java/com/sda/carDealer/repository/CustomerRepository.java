package com.sda.carDealer.repository;

import com.sda.carDealer.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Operator, Long> {
}
