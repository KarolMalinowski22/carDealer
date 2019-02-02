package com.sda.carDealer.controller;

import com.sda.carDealer.model.Customer;
import com.sda.carDealer.service.CustomerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {
    @Autowired
    private CustomerServiceInterface customerService;

    @RequestMapping("customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.getAll());
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
