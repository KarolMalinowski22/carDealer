package com.sda.carDealer.controller;

import com.sda.carDealer.model.User;
import com.sda.carDealer.service.UserServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserServiceInterface userService;
    @GetMapping("/registration")
    public String registrationForm(Model model){
        model.addAttribute("user", new User());
        return "registrationForm";
    }
    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "registrationForm";
        }

        userService.addNewUser(user);
        return "/registrationSuccessful";
    }
}
