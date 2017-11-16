package com.epam.hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@ToString(exclude = {"prescribableAppointmentTypes", "executableAppointmentTypes"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "roles", schema = "public")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",
            nullable = false)
    private Integer id;


    @Column(name = "name",
            nullable = false,
            length = -1)
    private String name;

}