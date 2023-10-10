package com.udacity.jdnd.course3.critter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.udacity.jdnd.course3.critter.entity.enums.PetType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDTO {
    private long id;
    private PetType type;
    private String name;
    private long ownerId;
    private LocalDate birthDate;
    private String notes;
}
