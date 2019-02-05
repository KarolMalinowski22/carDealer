package com.sda.carDealer.service;

import com.sda.carDealer.model.Car;
import com.sda.carDealer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CarServiceInterface {
    Car saveCar(Car car);
    List<Customer> setNewOwners();
    List<Car> getAllAvailable();
    Page<Car> getAllAvailablePaginated(Pageable pageable);
    List<Car> getAllAvailableShopOwned();
    List<Car> getSold();
    void deleteById(Long id);
    Optional<Car> findById(Long carId);
    void addNewCar(Customer customer, Car newCar);
}
