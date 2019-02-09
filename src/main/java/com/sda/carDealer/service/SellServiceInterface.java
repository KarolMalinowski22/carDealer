package com.sda.carDealer.service;

import com.sda.carDealer.model.Sell;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface SellServiceInterface {
    List<Sell> getAllSell();
    void save(Sell sell);
    List<Sell> getAllSellByMonth(LocalDateTime date);
    Optional<Sell> getById(Long id);
}
