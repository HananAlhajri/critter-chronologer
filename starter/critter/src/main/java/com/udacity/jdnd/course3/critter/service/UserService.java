package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.enums.EmployeeSkill;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface UserService {
    Customer saveCustomer(Customer customer, List<Long> petIds);

    List<Customer> getAllCustomers();

    Customer getOwnerByPet(long petId);

    Employee saveEmployee(Employee employee);

    Employee getEmployee(long employeeId);

    void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId);

    List<Employee> findEmployeesForService(Set<EmployeeSkill> skills);
}
