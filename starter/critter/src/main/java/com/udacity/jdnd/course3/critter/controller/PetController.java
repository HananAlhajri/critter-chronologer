package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Pet pet = mapPetDTOToPet(petDTO);
        petService.savePet(pet, petDTO.getOwnerId());

        return mapPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        return mapPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> petList = petService.getPets();

        return mapPetListToPetDTOList(petList);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petList = petService.getPetsByOwner(ownerId);
        return mapPetListToPetDTOList(petList);
    }

    private Pet mapPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();

        pet.setName(petDTO.getName());
        pet.setType(petDTO.getType());
        pet.setDateOfBirth(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());

        return pet;
    }

    private PetDTO mapPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();

        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getOwner().getId());
        petDTO.setBirthDate(pet.getDateOfBirth());
        petDTO.setNotes(pet.getNotes());

        return petDTO;
    }

    private List<PetDTO> mapPetListToPetDTOList(List<Pet> petList){

        List<PetDTO> petDTOList = new ArrayList<>();
        petList.forEach(
                pet -> {
                    petDTOList.add(mapPetToPetDTO(pet));
                }
        );

        return petDTOList;
    }
}
