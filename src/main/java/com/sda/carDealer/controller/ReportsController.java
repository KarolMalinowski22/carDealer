package com.sda.carDealer.controller;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Car;
import com.sda.carDealer.model.Sell;
import com.sda.carDealer.service.BuyServiceInterface;
import com.sda.carDealer.service.ReportServiceInterface;
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
    @Autowired
    private ReportServiceInterface reportService;
    @GetMapping("/sellReport")
    public String showSellReports(Model model){
        List<Sell> allSell = sellService.getAllSell();
        model.addAttribute("sells", allSell);
        model.addAttribute("profitMap", reportService.getProfit(allSell));
        return "sellReport";
    }

    @GetMapping("/buyReport")
    public String showBuyReports(Model model){
        model.addAttribute("buys", buyService.getAllBuy());
        return "buyReport";
    }


}
