package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule createSchedule(Schedule schedule);

    List<Schedule> getAllSchedules();

    List<Schedule> getScheduleForPet(Pet pet);

    List<Schedule> getScheduleForEmployee(Employee employee);

    List<Schedule> getScheduleForCustomer(Customer customer);
}
