package com.epam.hospital.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

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
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "role_prescribable_appointment_type",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_type_id"))
    private Set<AppointmentType> prescribableAppointmentType;

    @ManyToMany
    @JoinTable(name = "role_executable_appointment_type",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_type_id"))
    private Set<AppointmentType> executableAppointmentType;


}
