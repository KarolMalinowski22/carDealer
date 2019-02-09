package com.sda.carDealer.repository;

import com.sda.carDealer.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByVisibleTrue();
}
