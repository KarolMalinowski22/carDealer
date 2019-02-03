package com.sda.carDealer.controller;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Car;
import com.sda.carDealer.model.Sell;
import com.sda.carDealer.service.BuyServiceInterface;
import com.sda.carDealer.service.SellServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    private BuyServiceInterface buyService;
    @Autowired
    private SellServiceInterface sellService;
    @GetMapping("/sellReport")
    public String showSellReports(Model model){
        Map<Sell, BigDecimal> profit = new HashMap<>();
        List<Sell> allSell = sellService.getAllSell();
        for (Sell sell :
                allSell) {
            Car car = sell.getCar();
            if(car.getCustomers().get(0).getId() == 1){
                profit.put(sell, calculateProfit(buyService.getBuyByCar(car).get(), sell));
            }else {
                profit.put(sell, calculateProfitForeignCar(sell));
            }
        }
        model.addAttribute("sells", allSell);
        model.addAttribute("profitMap", profit);
        return "sellReport";
    }

    @GetMapping("/buyReport")
    public String showBuyReports(Model model){
        model.addAttribute("buys", buyService.getAllBuy());
        return "buyReport";
    }

    private BigDecimal calculateProfit(Buy buy, Sell sell) {
        return buy.getPrice().divide(sell.getPrice());
    }
    private BigDecimal calculateProfitForeignCar(Sell sell){
        return sell.getPrice().multiply(BigDecimal.valueOf(0.2));
    }
}
