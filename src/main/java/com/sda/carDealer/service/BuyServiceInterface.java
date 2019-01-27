package com.sda.carDealer.service;

import com.sda.carDealer.model.Buy;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BuyServiceInterface {
    List<Buy> getAllBuy();
    void createNewBuy(Buy buy);
}
