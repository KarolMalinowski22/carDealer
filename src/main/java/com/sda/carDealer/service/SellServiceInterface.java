package com.sda.carDealer.service;

import com.sda.carDealer.model.Sell;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellServiceInterface {
    List<Sell> getAllSell();
    void createNewSell(Sell sell);
}
