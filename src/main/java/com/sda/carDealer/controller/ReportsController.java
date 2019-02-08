package com.sda.carDealer.controller;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Sell;
import com.sda.carDealer.service.BuyServiceInterface;
import com.sda.carDealer.service.ReportServiceInterface;
import com.sda.carDealer.service.SellServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.expression.Dates;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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
    @RequestMapping("/sellReport")
    public String showSellReports(@ModelAttribute("date") String dateString, Model model){
        LocalDateTime date = parseMonthString(dateString);
        List<Sell> allSell = sellService.getAllSellByMonth(date);
        Date dateDisplay = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        model.addAttribute("date", dateDisplay);
        model.addAttribute("sells", allSell);
        model.addAttribute("profitMap", reportService.getProfit(allSell));
        return "sellReport";
    }

    @RequestMapping("/buyReport")
    public String showBuyReports(@ModelAttribute("date") String dateString, Model model){
        LocalDateTime date = parseMonthString(dateString);
        List<Buy> allBuy = buyService.getAllBuyByMonth(date);
        Date dateDisplay = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        model.addAttribute("date", dateDisplay);
        model.addAttribute("buys", allBuy);
        return "buyReport";
    }
    /**
     *
     * @param dateString must be pattern yyyy-MM
     * @return
     */
    private LocalDateTime parseMonthString(String dateString){
        if("".equals(dateString)) {
            dateString = LocalDateTime.now().toString();
        }else {
            dateString += "-01T00:00:00.000";
        }
        return LocalDateTime.parse(dateString);
    }

}
