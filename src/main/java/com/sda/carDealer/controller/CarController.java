package com.sda.carDealer.controller;

import com.sda.carDealer.controller.utilities.CurrentUser;
import com.sda.carDealer.model.*;
import com.sda.carDealer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
//@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarServiceInterface carService;
    @Autowired
    private BuyServiceInterface buyService;
    @Autowired
    private SellServiceInterface sellService;
    @Autowired
    private OperatorServiceInterface customerService;
    private Operator shop;
    private Integer pageSize = 10;


    @RequestMapping("/")
    public String showAll(Model model,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {
        model.addAttribute("userName", CurrentUser.getUsername());
        Integer currentPage = page.orElse(1);
        Integer currentSize = size.orElse(pageSize);
        Page<Car> carsPage = carService.getAllAvailablePaginated(PageRequest.of(currentPage - 1, currentSize));
        model.addAttribute("carsPage", carsPage);
        Integer totalPages = carsPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pagesNumbersList = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pagesNumbersList", pagesNumbersList);
        }
        return "cars";
    }

    @RequestMapping("/addCar")
    public String addCarForm(Model model) {
        model.addAttribute("operator", new Operator());
        model.addAttribute("newCar", new Car());
        return "addCarForm";
    }

    @PostMapping("/addCar")
    public String addCar(@Valid @ModelAttribute("newCar") Car newCar,
                         BindingResult bindingResultCar,
                         @ModelAttribute("owner") Operator operator,
                         BindingResult bindingResultCustomer,
                         Model model) {
        if (bindingResultCar.hasErrors() || bindingResultCustomer.hasErrors()) {
            model.addAttribute("operator", new Operator());
            model.addAttribute("newCar", new Car());
            return "addCarForm";
        }
        operator = customerService.addNewCustomer(operator);
        if (newCar.getOperators() == null) {
            newCar.setOperators(new ArrayList<>());
        }
        newCar.getOperators().add(operator);
        carService.addNewCar(operator, newCar);
        return "redirect:/";
    }

    @RequestMapping("/buyForm")
    public String buyCarForm(Model model) {
        model.addAttribute("operator", new Operator());
        model.addAttribute("newCar", new Car());
        return "buyCarForm";
    }

    @RequestMapping("/buy")
    public String buyCar(@Valid @ModelAttribute("newCar") Car newCar,
                         BindingResult bindingResult,
                         @ModelAttribute("price") String priceString,
                         @ModelAttribute("owner") Operator operator,
                         Model model) {
        ///if the car has been sold before
        if (sellService.getAllSell().stream().map(c -> c.getCar().getVin()).collect(Collectors.toList()).contains(newCar.getVin())) {
            model.addAttribute("hasBeenSoldBefore", "Ten samochód został już u nas sprzedany!");
            model.addAttribute("operator", new Operator());
            model.addAttribute("newCar", new Car());
            return "buyCarForm";
        }
        if (newCar.getOperators().size() > 0) {
            ///if car has more than one owner
            if (carService.findById(newCar.getId()).get().getOperators().size() > 1) {
                model.addAttribute("toManyOwners", "Aby kupić samochód, sprzedający musi być jedynym właścicielem pojazdu.");
                return "buyCarForm";
            }
        }
        if (bindingResult.hasErrors()) {
            return "buyCarForm";
        }
        Buy buy = new Buy();
        buy.setCar(newCar);
        buy.setDate(Timestamp.valueOf(LocalDateTime.now()));
        buy.setPrice(new BigDecimal(priceString));
        buy.setOperator(operator);
        //todo: Write test

        buyService.createNewBuy(buy);
        if (newCar != null) {
            carService.addNewCar(operator, newCar);
        }
        return "redirect:/";
    }

    @RequestMapping("/{id}/buy")
    public String buyCarPreparedForm(Model model, @PathVariable(name = "id") Long carId) {
        Car car = carService.findById(carId).get();
        model.addAttribute("car", car);
        return "buyCarPreparedForm";
    }

    @PostMapping("/buyPrepared")
    public String buyCarPrepared(@ModelAttribute(name = "id") Long carId,
                                 @ModelAttribute(name = "price")String price, Model model) {
        Car car = carService.findById(carId).get();
        ///if the car has been sold before
        if (sellService.getAllSell().stream().map(c -> c.getCar().getVin()).collect(Collectors.toList()).contains(car.getVin())) {
            model.addAttribute("hasBeenSoldBefore", "Ten samochód został już u nas sprzedany!");
            model.addAttribute("newCar", car);
            return "buyCarPreparedForm";
        }
        if (carService.findById(carId).get().getOperators().size() > 1) {
            model.addAttribute("toManyOwners", "Aby kupić samochód, sprzedający musi być jedynym właścicielem pojazdu.");
            return "buyCarPreparedForm";
        }
        if(shop == null){
            shop = customerService.getById(1L).get();
        }
        Buy buy = new Buy();
        buy.setPrice(new BigDecimal(price));
        buy.setDate(Timestamp.valueOf(LocalDateTime.now()));
        buy.setCar(car);
        buy.setOperator(car.getOperators().get(0));
        buyService.createNewBuy(buy);
        ArrayList<Operator> operators = new ArrayList<>();
        operators.add(shop);
        car.setOperators(operators);
        carService.saveCar(car);
        return "redirect:/";
    }

    @RequestMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id, Model model) {
        carService.deleteById(id);
        return "redirect:/cars";
    }

    @GetMapping("/{carId}/sell")
    public String sellCarForm(@PathVariable("carId") Long carId, Model model) {
        Optional<Car> carOptional = carService.findById(carId);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            model.addAttribute("car", car);
            model.addAttribute("sell", new Sell());
            model.addAttribute("customer", new Operator());
        }
        return "sellCarForm";
    }

    @PostMapping("/{carId}/sell")
    public String sellCar(@Valid @ModelAttribute("sell") Sell sell,
                          BindingResult bindingResult,
                          @ModelAttribute("customer") Operator operator,
                          @PathVariable("carId") Long carId,
                          Model model) {

        Car car = carService.findById(carId).get();

        if (bindingResult.hasErrors()) {
            model.addAttribute("car", car);
            return "sellCarForm";
        }
        for(Operator c : customerService.getAll()){
            if(c.equals(operator)){
                operator = c;
                break;
            }
        }
        sell.setCar(car);
        sell.setDate(Timestamp.valueOf(LocalDateTime.now()));
        sell.setOperator(operator);
        sellService.createNewSell(sell);
        car.getOperators().clear();
        car.getOperators().add(operator);
        carService.saveCar(car);
        return "redirect:/cars";
    }
}
