package com.sda.carDealer.service;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Car;
import com.sda.carDealer.model.Operator;
import com.sda.carDealer.model.Sell;
import com.sda.carDealer.repository.BuyRepository;
import com.sda.carDealer.repository.CarRepository;
import com.sda.carDealer.repository.CustomerRepository;
import com.sda.carDealer.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Operator> setNewOwners() {
        return null;
    }

    @Override
    public List<Car> getAllAvailable() {
        List<Car> cars = carRepository.findAll();
        cars.removeAll(carRepository.findAllById(sellRepository.getAllCarsId()));
        return cars;
    }

    @Override
    public Page<Car> getAllAvailablePaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Car> allAvailable = getAllAvailable();
        List<Car> cars;
        if(startItem > allAvailable.size()){
            cars = Collections.emptyList();
        }else{
            cars = allAvailable.subList(startItem, Math.min(startItem + pageSize, allAvailable.size()));
        }
        Page<Car> carsPage = new PageImpl<>(cars, PageRequest.of(currentPage, pageSize), allAvailable.size());
        return carsPage;
    }

    @Override
    public List<Car> getAllAvailableShopOwned() {
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
    public void addNewCar(Operator operator, Car newCar) {
        customerRepository.save(operator);
        carRepository.save(newCar);
    }

    @Override
    public Optional<Car> findById(Long carId) {
        return carRepository.findById(carId);
    }
}
