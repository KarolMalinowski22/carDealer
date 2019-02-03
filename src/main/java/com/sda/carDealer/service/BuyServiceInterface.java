package com.sda.carDealer.service;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Car;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BuyServiceInterface {
    List<Buy> getAllBuy();
    void createNewBuy(Buy buy);
    Optional<Buy> getBuyByCar(Car car);
}
