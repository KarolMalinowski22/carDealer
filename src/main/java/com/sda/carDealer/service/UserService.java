package com.sda.carDealer.service;

import com.sda.carDealer.model.User;
import com.sda.carDealer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;

    /**
     * Checks if conditions to create new user are met and adds the user to database.
     */
    @Override
    public User addNewUser(User user) {
        if(emailExists(user.getEmail())||loginExists(user.getLogin())){
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserByUserName(String login) {
        return userRepository.findFirstByLogin(login);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Checks if this email already exist in database.
     */
    @Override
    public boolean emailExists(String email) {
        User firstByEmail = userRepository.findFirstByEmail(email).get();
        return firstByEmail != null;
    }

    /**
     * Checks if this login already exist in database.
     */
    @Override
    public boolean loginExists(String login) {
        User firstByLogin = userRepository.findFirstByLogin(login);
        return firstByLogin != null;
    }
}
