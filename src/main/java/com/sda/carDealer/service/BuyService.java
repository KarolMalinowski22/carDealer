package com.sda.carDealer.service;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.repository.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BuyService implements BuyServiceInterface {
    @Autowired
    private BuyRepository buyRepository;
    @Override
    public List<Buy> getAllBuy() {
        return buyRepository.findAll();
    }

    @Override
    public void createNewBuy(Buy buy) {
        buyRepository.save(buy);
    }
}
