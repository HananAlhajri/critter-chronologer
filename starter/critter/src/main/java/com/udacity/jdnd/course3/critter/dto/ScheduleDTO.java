package com.udacity.jdnd.course3.critter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.udacity.jdnd.course3.critter.dto.annotation.DateNotInPast;
import com.udacity.jdnd.course3.critter.entity.enums.EmployeeSkill;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO {
    private long id;
    @NotNull(message = "employee ids are required")
    private List<Long> employeeIds;
    @NotNull(message = "pet ids are required")
    private List<Long> petIds;
    @NotNull(message = "date is required")
    @DateNotInPast
    private LocalDate date;
    private Set<EmployeeSkill> activities;
}
