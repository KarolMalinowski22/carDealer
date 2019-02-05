package com.sda.carDealer.controller;

import com.sda.carDealer.model.Buy;
import com.sda.carDealer.model.Car;
import com.sda.carDealer.model.Customer;
import com.sda.carDealer.model.Sell;
import com.sda.carDealer.repository.SellRepository;
import com.sda.carDealer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final Long shopId = 1L;
    @Autowired
    private CarServiceInterface carService;
    @Autowired
    private BuyServiceInterface buyService;
    @Autowired
    private SellServiceInterface sellService;
    @Autowired
    private CustomerServiceInterface customerService;

    @RequestMapping()
    public String showAll(Model model,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {
        Integer currentPage = page.orElse(1);
        Integer currentSize = size.orElse(5);
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
        model.addAttribute("customer", new Customer());
        model.addAttribute("newCar", new Car());
        return "addCarForm";
    }

    @PostMapping("/addCar")
    public String addCar(@Valid @ModelAttribute("newCar") Car newCar,
                         BindingResult bindingResultCar,
                         @ModelAttribute("owner") Customer customer,
                         BindingResult bindingResultCustomer,
                         Model model) {
        if (bindingResultCar.hasErrors() || bindingResultCustomer.hasErrors()) {
            model.addAttribute("customer", new Customer());
            model.addAttribute("newCar", new Car());
            return "addCarForm";
        }
        customer = customerService.addNewCustomer(customer);
        if (newCar.getCustomers() == null) {
            newCar.setCustomers(new ArrayList<>());
        }
        newCar.getCustomers().add(customer);
        carService.addNewCar(customer, newCar);
        return "redirect:/";
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
            model.addAttribute("customer", new Customer());
            model.addAttribute("newCar", new Car());
            return "buyCarForm";
        }
        if (newCar.getCustomers().size() > 0) {
            ///if car has more than one owner
            if (carService.findById(newCar.getId()).get().getCustomers().size() > 1) {
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
        buy.setCustomer(customer);
        //todo: Write test

        buyService.createNewBuy(buy);
        if (newCar != null) {
            carService.addNewCar(customer, newCar);
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
        if (carService.findById(carId).get().getCustomers().size() > 1) {
            model.addAttribute("toManyOwners", "Aby kupić samochód, sprzedający musi być jedynym właścicielem pojazdu.");
            return "buyCarPreparedForm";
        }
        Buy buy = new Buy();
        buy.setPrice(new BigDecimal(price));
        buy.setDate(Timestamp.valueOf(LocalDateTime.now()));
        buy.setCar(car);
        buy.setCustomer(car.getCustomers().get(0));
        buyService.createNewBuy(buy);
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(customerService.getById(shopId).get());
        car.setCustomers(customers);
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
            model.addAttribute("customer", new Customer());
        }
        return "sellCarForm";
    }

    @PostMapping("/{carId}/sell")
    public String sellCar(@Valid @ModelAttribute("sell") Sell sell,
                          BindingResult bindingResult,
                          @ModelAttribute("customer") Customer customer,
                          @PathVariable("carId") Long carId,
                          Model model) {

        Car car = carService.findById(carId).get();

        if (bindingResult.hasErrors()) {
            model.addAttribute("car", car);
            return "sellCarForm";
        }
        for(Customer c : customerService.getAll()){
            if(c.equals(customer)){
                customer = c;
                break;
            }
        }
        sell.setCar(car);
        sell.setDate(Timestamp.valueOf(LocalDateTime.now()));
        sell.setCustomer(customer);
        sellService.createNewSell(sell);
        car.getCustomers().clear();
        car.getCustomers().add(customer);
        carService.saveCar(car);
        return "redirect:/cars";
    }
}
