package com.sda.carDealer.repository;

import com.sda.carDealer.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CarRepository extends JpaRepository<Car, Long> {
}
