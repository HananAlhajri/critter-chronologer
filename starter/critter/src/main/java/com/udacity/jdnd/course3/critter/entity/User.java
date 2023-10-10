package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;
}
