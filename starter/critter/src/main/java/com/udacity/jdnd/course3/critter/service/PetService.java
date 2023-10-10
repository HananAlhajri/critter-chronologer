package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Pet;

import java.util.List;

public interface PetService {
    void savePet(Pet pet, Long ownerId);

    Pet getPet(long petId);

    List<Pet> getPets();

    List<Pet> getPetsByOwner(long ownerId);
}
