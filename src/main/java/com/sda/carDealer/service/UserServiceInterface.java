package com.sda.carDealer.service;

import com.sda.carDealer.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserServiceInterface {
    List<User> getAllUsers();
    User addNewUser(User user);
    User getUserByUserName(String login);
    Optional<User> getUserById(Long id);
    boolean emailExists(String email);
    boolean loginExists(String login);
    User updateUser(User user);
}
