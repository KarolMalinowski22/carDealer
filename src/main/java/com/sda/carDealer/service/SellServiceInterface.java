package com.sda.carDealer.service;

import com.sda.carDealer.model.Sell;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public interface SellServiceInterface {
    List<Sell> getAllSell();
    void createNewSell(Sell sell);

    List<Sell> getAllSellByMonth(LocalDateTime date);
}
