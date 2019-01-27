package com.sda.carDealer.service;

import com.sda.carDealer.model.Sell;
import com.sda.carDealer.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SellService implements SellServiceInterface{
    @Autowired
    private SellRepository sellRepository;
    @Override
    public List<Sell> getAllSell() {
        return sellRepository.findAll();
    }

    @Override
    public void createNewSell(Sell sell) {
        sellRepository.save(sell);
    }
}
