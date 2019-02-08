package com.sda.carDealer.service;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Car;
import com.sda.carDealer.repository.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Buy> getBuyByCar(Car car) {
        return buyRepository.findByCar(car);
    }

    @Override
    public List<Buy> getAllBuyByMonth(LocalDateTime dateTime) {
        Timestamp from = Timestamp.valueOf(dateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0));
        Timestamp to = Timestamp.valueOf(dateTime.plusMonths(1).withDayOfMonth(1).minusDays(1).withHour(23).withMinute(59).withSecond(59));
        List<Buy> allByDateBetween = buyRepository.findAllByDateBetween(from, to);
        if(allByDateBetween.size() > 0){
            return allByDateBetween;
        }else{
            return Collections.emptyList();
        }
    }
}
