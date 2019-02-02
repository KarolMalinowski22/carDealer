package com.sda.carDealer.service;

import com.sda.carDealer.model.Car;
import com.sda.carDealer.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CarServiceInterface {
    List<Car> getAllAvailable();

    List<Car> getSold();

    void deleteById(Long id);

    Optional<Car> findById(Long carId);

    void addNewCar(Customer customer, Car newCar);
}
