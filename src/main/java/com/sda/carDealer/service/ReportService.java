package com.sda.carDealer.service;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Car;
import com.sda.carDealer.model.Sell;
import com.sda.carDealer.repository.BuyRepository;
import com.sda.carDealer.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService implements ReportServiceInterface{
    private BigDecimal TAX = new BigDecimal("0.19");
    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private SellRepository sellRepository;
    @Override
    public List<String> generateSellReport() {
        List<String> report = new ArrayList<>();

        return null;
    }

    @Override
    public List<String> generateBuyReport() {
        return null;
    }

    public Map<Sell, BigDecimal> getProfit(List<Sell> allSell){
        Map<Sell, BigDecimal> profit = new HashMap<>();
        for (Sell sell :
                allSell) {
            Car car = sell.getCar();
            if(car.getOperators().get(0).getId() == 1){
                profit.put(sell, calculateProfit(buyRepository.findByCar(car).get(), sell));
            }else {
                profit.put(sell, calculateProfitForeignCar(sell));
            }
        }
        return profit;
    }
    private BigDecimal calculateProfit(Buy buy, Sell sell) {
        BigDecimal beforeTax = sell.getPrice().subtract( buy.getPrice());
        BigDecimal afterTax = beforeTax.subtract(beforeTax.multiply(TAX));
        return afterTax;
    }
    private BigDecimal calculateProfitForeignCar(Sell sell){
        return sell.getPrice().multiply(BigDecimal.valueOf(0.2));
    }
}
