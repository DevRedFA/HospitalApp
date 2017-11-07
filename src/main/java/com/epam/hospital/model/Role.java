package com.epam.hospital.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(exclude = {"users", "prescribableAppointmentType", "executableAppointmentType"})
@Entity
@Table(name = "roles", schema = "public")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "roles_prescribable_appointments_types",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_type_id"))
    private List<AppointmentType> prescribableAppointmentType;

    @ManyToMany
    @JoinTable(name = "roles_executable_appointments_types",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_type_id"))
    private List<AppointmentType> executableAppointmentType;

}
