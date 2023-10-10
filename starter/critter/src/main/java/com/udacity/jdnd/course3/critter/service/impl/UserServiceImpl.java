package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repo.CustomerRepository;
import com.udacity.jdnd.course3.critter.repo.PetRepository;
import com.udacity.jdnd.course3.critter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        List<Pet> petList = new ArrayList<>();

        if(customerDTO.getPetIds() != null && !customerDTO.getPetIds().isEmpty()) {
            customerDTO.getPetIds().forEach(
                    id -> petList.add(
                            petRepository.findById(id)
                                    .orElseThrow(PetNotFoundException::new)
                    ));
        }

        Customer customer = mapCustomerDTOToCustomer(customerDTO);
        customer.setPetList(petList);
        customerRepository.save(customer);

        return mapCustomerToCustomerDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDTO> customerDTOList = new ArrayList<>();

        customerList.forEach(
                customer -> customerDTOList.add(mapCustomerToCustomerDTO(customer))
        );

        return customerDTOList;
    }

    @Override
    public CustomerDTO getOwnerByPet(long petId) {

        Pet pet = petRepository.findById(petId).orElseThrow(PetNotFoundException::new);

        Customer customer = pet.getOwner();

        return mapCustomerToCustomerDTO(customer);
    }

    private Customer mapCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();

        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());

        return customer;
    }

    private CustomerDTO mapCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        List<Long> petIds = new ArrayList<>();

        if(customer.getPetList() != null ){
            customer.getPetList().forEach(pet -> {
                petIds.add(pet.getId());
            });
        }

        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setPetIds(petIds);
        customerDTO.setNotes(customer.getNotes());

        return customerDTO;
    }
}
