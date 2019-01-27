package com.sda.carDealer.service;

import com.sda.carDealer.model.Owner;
import com.sda.carDealer.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService implements OwnerServiceInterface {
    private OwnerRepository ownerRepository;

    @Override
    public void addNewOwner(Owner owner){
        ownerRepository.save(owner);
    }

    @Override
    public List<Owner> getAll() {
        return ownerRepository.findAll();
    }
}
