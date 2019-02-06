package com.sda.carDealer.service;

import com.sda.carDealer.model.Sell;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface ReportServiceInterface {
    List<String> generateSellReport();
    List<String> generateBuyReport();
    Map<Sell, BigDecimal> getProfit(List<Sell> allSell);
}
