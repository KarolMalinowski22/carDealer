package com.sda.carDealer.service;

import com.sda.carDealer.model.Owner;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OwnerServiceInterface {
    void addNewOwner(Owner owner);
    List<Owner> getAll();
}
