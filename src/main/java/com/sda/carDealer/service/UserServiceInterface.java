package com.sda.carDealer.service;

import com.sda.carDealer.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserServiceInterface {
    User addNewUser(User user);
    User getUserByUserName(String login);
    User getUserById(Long id);
    boolean emailExists(String email);
    boolean loginExists(String login);
}
