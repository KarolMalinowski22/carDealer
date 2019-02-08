package com.sda.carDealer.repository;

import com.sda.carDealer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByLogin(String login);
    Optional<User> findFirstByEmail(String email);
}
