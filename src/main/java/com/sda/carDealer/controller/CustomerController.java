package com.sda.carDealer.controller;

import com.sda.carDealer.model.Customer;
import com.sda.carDealer.service.CustomerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CustomerController {
    @Autowired
    private CustomerServiceInterface customerService;

    @RequestMapping("customers")
    public String customers(Model model,
                            @RequestParam("page")Optional<Integer> pageOptional,
                            @RequestParam("size") Optional<Integer> sizeOptional) {
        Integer size = sizeOptional.orElse(5);
        Integer page = pageOptional.orElse(1);
        Page<Customer> customersPage = customerService.getAllPaginated(PageRequest.of(page - 1, size));
        model.addAttribute("customersPage", customersPage);
        Integer totalPages = customersPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pagesNumbersList = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pagesNumbersList", pagesNumbersList);
        }
        return "customers";
    }

    @RequestMapping("customerRegistration")
    public String customerRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerRegistrationForm";
    }

    @PostMapping("customerRegistration")
    public String customerRegistrationForm(@ModelAttribute("customer") Customer customer, Model model) {
        for (Customer customerRegistered : customerService.getAll()) {
            if (customerRegistered.equals(customer)) {
                model.addAttribute("duplicate", "Już istnieje klient o takich danych");
                return "customerRegistrationForm";
            }
        }
        customerService.addNewCustomer(customer);
        return "redirect:/customers";
    }
}
