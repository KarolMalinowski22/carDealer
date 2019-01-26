package com.sda.carDealer.repository;

import com.sda.carDealer.model.Sell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellRepository extends JpaRepository<Sell, Long> {
}
