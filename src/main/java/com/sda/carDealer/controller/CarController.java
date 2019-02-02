package com.sda.carDealer.controller;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Car;
import com.sda.carDealer.model.Customer;
import com.sda.carDealer.model.Sell;
import com.sda.carDealer.repository.SellRepository;
import com.sda.carDealer.service.BuyServiceInterface;
import com.sda.carDealer.service.CarServiceInterface;
import com.sda.carDealer.service.SellServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
//@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarServiceInterface carService;
    @Autowired
    private SellRepository sellRepository;
    @Autowired
    private BuyServiceInterface buyService;
    @Autowired
    private SellServiceInterface sellService;

    @RequestMapping()
    public String showAll(Model model) {
        List<Car> allCars = carService.getAllAvailable();

        model.addAttribute("carList", allCars);
        return "cars";
    }

    @RequestMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id, Model model) {
        carService.deleteById(id);
        return "redirect:/cars";
    }

    @RequestMapping("/buyForm")
    public String buyCarForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("newCar", new Car());
        return "buyCarForm";
    }

    @RequestMapping("/buy")
    public String buyCar(@Valid @ModelAttribute("newCar") Car newCar,
                         BindingResult bindingResult,
                         @ModelAttribute("price") String priceString,
                         @ModelAttribute("owner") Customer customer,
                         Model model) {
        ///if the car has been sold before
        if (sellService.getAllSell().stream().map(c -> c.getCar().getVin()).collect(Collectors.toList()).contains(newCar.getVin())) {
            model.addAttribute("hasBeenSoldBefore", "Ten samochód został już u nas sprzedany!");
            return "buyCarForm";
        }
        if (bindingResult.hasErrors()) {
            return "buyCarForm";
        }
        Buy buy = new Buy();
        buy.setCar(newCar);
        buy.setDate(Timestamp.valueOf(LocalDateTime.now()));
        buy.setPrice(new BigDecimal(priceString));
        //todo: Write test

        buyService.createNewBuy(buy);
        if (newCar != null) {
            carService.addNewCar(customer, newCar);
        }
        return "redirect:/";
    }

    @GetMapping("/{carId}/sell")
    public String sellCarForm(@PathVariable("carId") Long carId, Model model) {
        Optional<Car> carOptional = carService.findById(carId);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            model.addAttribute("car", car);
            Sell sell = new Sell();
            model.addAttribute("sell", sell);
        }
        return "sellCarForm";
    }

    @PostMapping("/{carId}/sell")
    public String sellCar(@Valid @ModelAttribute("sell") Sell sell,
                          BindingResult bindingResult,
                          @PathVariable("carId") Long carId,
                          Model model) {

        Car car = carService.findById(carId).get();

        if (bindingResult.hasErrors()) {
            model.addAttribute("car", car);
            return "sellCarForm";
        }
        sell.setCar(car);
        sell.setDate(Timestamp.valueOf(LocalDateTime.now()));
        sellService.createNewSell(sell);
        return "redirect:/cars";
    }
}
