package com.sda.carDealer.service;

import com.sda.carDealer.model.Sell;
import com.sda.carDealer.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SellService implements SellServiceInterface{
    @Autowired
    private SellRepository sellRepository;
    @Override
    public List<Sell> getAllSell() {
        return sellRepository.findAll();
    }

    @Override
    public void save(Sell sell) {
        sellRepository.save(sell);
    }
    @Override
    public List<Sell> getAllSellByMonth(LocalDateTime dateTime) {
        Timestamp from = Timestamp.valueOf(dateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0));
        Timestamp to = Timestamp.valueOf(dateTime.plusMonths(1).withDayOfMonth(1).minusDays(1).withHour(23).withMinute(59).withSecond(59));
        List<Sell> allByDateBetween = sellRepository.findAllByDateBetweenAndCanceledFalse(from, to);
        if(allByDateBetween.size() > 0){
            return allByDateBetween;
        }else{
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Sell> getById(Long id) {
        return sellRepository.findById(id);
    }
}
