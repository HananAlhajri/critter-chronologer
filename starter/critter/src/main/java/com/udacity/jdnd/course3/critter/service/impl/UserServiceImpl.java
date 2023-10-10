package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repo.CustomerRepository;
import com.udacity.jdnd.course3.critter.repo.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repo.PetRepository;
import com.udacity.jdnd.course3.critter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Customer saveCustomer(Customer customer, List<Long> petIds) {

        List<Pet> petList = new ArrayList<>();

        if(petIds != null && !petIds.isEmpty()) {
            petIds.forEach(
                    id -> petList.add(
                            petRepository.findById(id)
                                    .orElseThrow(PetNotFoundException::new)
                    ));
        }

        customer.setPetList(petList);

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    @Override
    public Customer getOwnerByPet(long petId) {

        Pet pet = petRepository.findById(petId).orElseThrow(PetNotFoundException::new);

        return pet.getOwner();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployee(long employeeId) {

        return employeeRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);

        employee.setDaysAvailable(daysAvailable);

    }

    @Override
    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills) {
        List<Employee> employeeList = employeeRepository.findAll();

        List<Employee> result = new ArrayList<>();
        employeeList.forEach(
                employee -> {
                    if(employee.getSkills().containsAll(skills))
                        result.add(employee);
                }
        );

        return result;
    }

    @Override
    public Customer findCustomer(long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }


}
