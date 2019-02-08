package com.sda.carDealer.repository;

import com.sda.carDealer.model.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface SellRepository extends JpaRepository<Sell, Long> {
    @Query("select c.id from Sell s inner join s.car c")
    List<Long> getAllCarsId();
    List<Sell> findAllByDateBetween(Timestamp from, Timestamp to);
}
