package com.sda.carDealer.service;

import com.sda.carDealer.model.User;
import com.sda.carDealer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<User> firstByLoginOpt = userRepository.findFirstByLogin(login);
        if(firstByLoginOpt.isPresent()){
            return firstByLoginOpt.get();
        }else {
            return null;
        }
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
        Optional<User> firstByEmailOpt = userRepository.findFirstByEmail(email);
        if(firstByEmailOpt.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Checks if this login already exist in database.
     */
    @Override
    public boolean loginExists(String login) {
        Optional<User> firstByLoginOpt = userRepository.findFirstByLogin(login);
        if(firstByLoginOpt.isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
