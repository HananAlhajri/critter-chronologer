package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repo.CustomerRepository;
import com.udacity.jdnd.course3.critter.repo.PetRepository;
import com.udacity.jdnd.course3.critter.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void savePet(Pet pet, Long ownerId) {
        Customer customer = customerRepository.findById(ownerId)
                .orElseThrow(CustomerNotFoundException::new);

        pet.setOwner(customer);
        customer.getPetList().add(pet);

        try {
            petRepository.save(pet);
            customerRepository.save(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pet getPet(long petId) {

        return petRepository.findById(petId)
                .orElseThrow(PetNotFoundException::new);
    }

    @Override
    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getPetsByOwner(long ownerId) {
        Customer owner = customerRepository.findById(ownerId)
                .orElseThrow(CustomerNotFoundException::new);

        return petRepository.findPetListByOwner(owner);
    }

}
