package com.sda.carDealer.repository;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BuyRepository extends JpaRepository<Buy, Long> {
    @Query("select c.id from Buy b inner join b.car c")
    List<Long> getAllCarsId();
    Optional<Buy> findByCar(Car car);
}
