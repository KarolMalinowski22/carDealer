package com.sda.carDealer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService implements ReportServiceInterface{
    @Autowired
    private BuyServiceInterface buyService;
    @Autowired
    private SellServiceInterface sellService;
    @Override
    public List<String> generateSellReport() {
        List<String> report = new ArrayList<>();

        return null;
    }

    @Override
    public List<String> generateBuyReport() {
        return null;
    }
}
