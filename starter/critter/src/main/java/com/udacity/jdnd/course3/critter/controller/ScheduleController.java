package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;
    private final PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO) {
        List<Pet> pets = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();

        if(scheduleDTO.getPetIds() != null){
            scheduleDTO.getPetIds().forEach(
                    id -> {
                        pets.add(petService.getPet(id));
                    }
            );
        }

        if(scheduleDTO.getEmployeeIds() != null){
            scheduleDTO.getEmployeeIds().forEach(
                    id ->
                            employeeList.add(userService.getEmployee(id))
            );
        }
        Schedule schedule = mapScheduleDTOToSchedule(scheduleDTO);
        schedule.setPetList(pets);
        schedule.setEmployeeList(employeeList);

        scheduleService.createSchedule(schedule);

        return mapScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        scheduleList.forEach(
                schedule -> {
                    scheduleDTOList.add(mapScheduleToScheduleDTO(schedule));
                }
        );
        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        Pet pet = petService.getPet(petId);
        pet.setOwner(userService.getOwnerByPet(petId));

        List<Schedule> scheduleList = scheduleService.getScheduleForPet(pet);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        scheduleList.forEach(
                schedule -> {
                    scheduleDTOList.add(mapScheduleToScheduleDTO(schedule));
                }
        );
        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        Employee employee = userService.getEmployee(employeeId);
        List<Schedule> scheduleList = scheduleService.getScheduleForEmployee(employee);

        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        scheduleList.forEach(
                schedule -> {
                    scheduleDTOList.add(mapScheduleToScheduleDTO(schedule));
                }
        );
        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        Customer customer = userService.findCustomer(customerId);
        List<Schedule> scheduleList = scheduleService.getScheduleForCustomer(customer);

        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        scheduleList.forEach(
                schedule -> {
                    scheduleDTOList.add(mapScheduleToScheduleDTO(schedule));
                }
        );
        return scheduleDTOList;
    }

    private ScheduleDTO mapScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        List<Long> employeeIdsList = new ArrayList<>();
        List<Long> petIdsList = new ArrayList<>();

        schedule.getEmployeeList().forEach(employee ->
                employeeIdsList.add(employee.getId()));

        schedule.getPetList().forEach(pet ->
                petIdsList.add(pet.getId()));

        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(employeeIdsList);
        scheduleDTO.setPetIds(petIdsList);
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());

        return scheduleDTO;
    }

    private Schedule mapScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();

        scheduleDTO.getEmployeeIds().forEach(id ->
                employeeList.add(userService.getEmployee(id)));

        scheduleDTO.getPetIds().forEach(id ->
                petList.add(petService.getPet(id)));

        schedule.setEmployeeList(employeeList);
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setPetList(petList);
        schedule.setDate(scheduleDTO.getDate());

        return schedule;
    }
}
