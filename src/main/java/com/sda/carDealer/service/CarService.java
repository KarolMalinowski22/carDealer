package com.sda.carDealer.service;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Car;
import com.sda.carDealer.model.Customer;
import com.sda.carDealer.model.Sell;
import com.sda.carDealer.repository.BuyRepository;
import com.sda.carDealer.repository.CarRepository;
import com.sda.carDealer.repository.CustomerRepository;
import com.sda.carDealer.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarService implements CarServiceInterface{
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private SellRepository sellRepository;
    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Car> getAllAvailable() {
        List<Buy> buys = buyRepository.findAll();
        List<Sell> sells = sellRepository.findAll();
        List<Car> cars = buys.stream().map(buy -> buy.getCar()).collect(Collectors.toList());
        List<Car> carsSold = sells.stream().map(sell -> sell.getCar()).collect(Collectors.toList());
        cars.removeAll(carsSold);
        return cars;
    }

    @Override
    public List<Car> getSold() {
        List<Sell> sells = sellRepository.findAll();
        List<Car> cars = sells.stream().map(sell -> sell.getCar()).collect(Collectors.toList());
        return cars;
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public void addNewCar(Customer customer, Car newCar) {
        customerRepository.save(customer);
        carRepository.save(newCar);
    }

    @Override
    public Optional<Car> findById(Long carId) {
        return carRepository.findById(carId);
    }
}
