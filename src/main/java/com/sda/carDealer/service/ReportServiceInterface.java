package com.sda.carDealer.service;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ReportServiceInterface {
    List<String> generateSellReport();
    List<String> generateBuyReport();
}
