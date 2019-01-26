package com.sda.carDealer.controller;

import com.sda.carDealer.model.Car;
import com.sda.carDealer.model.Sell;
import com.sda.carDealer.repository.CarRepository;
import com.sda.carDealer.repository.SellRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private SellRepository sellRepository;
    @RequestMapping()
    public String showAll(Model model) {
        List<Car> allCars = carRepository.findAll();

            model.addAttribute("carList", allCars);
            return "cars";



    }
    @RequestMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id, Model model){
        carRepository.deleteById(id);
        return "redirect:/cars";
    }
    @RequestMapping("addForm")
    public String addCarForm(Model model){
        model.addAttribute("newCar", new Car());
        return "addCarForm";
    }
    @RequestMapping("add")
    public String addCar(@ModelAttribute("newCar")Car newCar, Model model){
        if(newCar != null) {
            carRepository.save(newCar);
        }
        return "redirect:/cars";
    }
    @RequestMapping("/{id}/sellForm")
    public String sellCarForm(@PathVariable("id")Long carId, Model model){
        Car car = carRepository.findById(carId).get();
        Sell sell = new Sell();
        model.addAttribute("sell", sell);
        model.addAttribute("car", car);

        return "sellCarForm";
    }
    @RequestMapping("/sell")
    @PostMapping
    public String sellCar(@ModelAttribute("carId") Long carId, @ModelAttribute("sell") Sell sell, Model model){
        sell.setDate(Timestamp.valueOf(LocalDateTime.now()));
        sell.setCar(carRepository.findById(carId).get());
        sellRepository.save(sell);
        carRepository.deleteById(sell.getCar().getId());
        return "redirect:/cars";
    }
}
