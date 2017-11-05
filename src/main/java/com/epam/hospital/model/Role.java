package com.epam.hospital.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Data
@ToString(exclude = "users")
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;


    @ManyToMany
    @JoinTable(name = "role_appointment_types", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_type_id"))
    private Collection<AppointmentType> appointmentTypes;

}
