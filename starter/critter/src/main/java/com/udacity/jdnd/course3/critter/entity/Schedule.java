package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.entity.enums.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Employee> employeeList;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Pet> petList;

}
