package com.sda.carDealer.repository;

import com.sda.carDealer.model.Buy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyRepository extends JpaRepository<Buy, Long> {
    @Query("select c.id from Buy b inner join b.car c")
    List<Long> getAllCarsId();
}
