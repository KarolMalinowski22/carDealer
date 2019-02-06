package com.sda.carDealer.service;

import com.sda.carDealer.model.AppUserDetails;
import com.sda.carDealer.model.User;
import com.sda.carDealer.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@Service
public class AppUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public AppUserDetails loadUserByUsername(String username) {
        return new AppUserDetails(userRepository.findFirstByEmail(username).orElse(null));

    }
}
