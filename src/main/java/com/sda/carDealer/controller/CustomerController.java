package com.sda.carDealer.controller;

import com.sda.carDealer.model.Operator;
import com.sda.carDealer.service.OperatorServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CustomerController {
    @Autowired
    private OperatorServiceInterface operatorService;
    private Integer pageSize = 10;

    @RequestMapping("/operators")
    public String customers(Model model,
                            @RequestParam("page")Optional<Integer> pageOptional,
                            @RequestParam("size") Optional<Integer> sizeOptional) {
        Integer size = sizeOptional.orElse(pageSize);
        Integer page = pageOptional.orElse(1);
        Page<Operator> customersPage = operatorService.getAllPaginated(PageRequest.of(page - 1, size));
        model.addAttribute("operatorsPage", customersPage);
        Integer totalPages = customersPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pagesNumbersList = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pagesNumbersList", pagesNumbersList);
        }
        return "operators";
    }

    @RequestMapping("/operatorRegistration")
    public String customerRegistrationForm(Model model) {
        model.addAttribute("operator", new Operator());
        return "operatorRegistrationForm";
    }

    @PostMapping("/operatorRegistration")
    public String customerRegistrationForm(@ModelAttribute("operator") Operator operator, Model model) {
        if(checkIfThereIsSuchOperator(operator)){
            model.addAttribute("duplicate", "Już istnieje podmiot o takich danych");
            return "operatorRegistrationForm";
        }
        operatorService.addNewCustomer(operator);
        return "redirect:/operators";
    }
    @RequestMapping("/{operatorId}/editOperator")
    public String editOperatorForm(Model model, @PathVariable(name = "operatorId")Long operatorId){
        model.addAttribute("operator", operatorService.getById(operatorId).get());
        return "editOperatorForm";
    }
    @PostMapping("/editOperator")
    public String editOperator(Model model, @ModelAttribute(name = "operator")Operator operator){
        if(checkIfThereIsSuchOperator(operator)){
            model.addAttribute("operator", operator);
            model.addAttribute("duplicate", "Już istnieje podmiot o takich danych");
            return "editOperatorForm";
        }
        operatorService.addNewCustomer(operator);
        return "redirect:/operators";
    }
    private boolean checkIfThereIsSuchOperator(Operator operator){
        for (Operator operatorRegistered : operatorService.getAll()) {
            if (operatorRegistered.equals(operator)) {
                return true;
            }
        }
        return false;
    }
}
