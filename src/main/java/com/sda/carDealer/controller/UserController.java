package com.sda.carDealer.controller;

import com.sda.carDealer.controller.utilities.CurrentUser;
import com.sda.carDealer.model.User;
import com.sda.carDealer.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserServiceInterface userService;
    @RequestMapping("/showUsers")
    public String showUsers(Model model){
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("userName", CurrentUser.getUsername());
        model.addAttribute("allUsers", allUsers);
        return "users";
    }
    @GetMapping("/register")
    public String registrationForm(Model model){
        model.addAttribute("user", new User());
        return "registrationForm";
    }
    @PostMapping("/register")
    public String registration(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "registrationForm";
        }
        user.getRoles().add("ROLE_USER");
        userService.addNewUser(user);
        return "registrationSuccessful";
    }
    @RequestMapping("/{id}/editUser")
    public String editUserForm(Model model, @PathVariable(name="id")Long userId){
        Optional<User> userByIdOpt = userService.getUserById(userId);
        if(userByIdOpt.isPresent()) {
            model.addAttribute("user", userByIdOpt.get());
        }else{
            model.addAttribute("error", "nie ma usera o takim id");
        }
        return "editUserForm";
    }
    @PostMapping("/editUser")
    public String editUser(Model model,
                           @ModelAttribute(name="id")Long userId, @ModelAttribute(name="adminRole")String adminRole,
                           @ModelAttribute(name = "ownerRole")String ownerRole){
        User user = userService.getUserById(userId).get();

        if("ROLE_ADMIN".equals(adminRole)){
            if(!user.getRoles().contains(adminRole)) {
                user.getRoles().add(adminRole);
            }
        }else{
            user.getRoles().remove("ROLE_ADMIN");
        }
        if("ROLE_OWNER".equals(ownerRole)){
            if(!user.getRoles().contains(ownerRole)) {
                user.getRoles().add(ownerRole);
            }
        }else{
            user.getRoles().remove("ROLE_OWNER");
        }
        userService.updateUser(user);
        return "redirect:/showUsers";
    }
}
