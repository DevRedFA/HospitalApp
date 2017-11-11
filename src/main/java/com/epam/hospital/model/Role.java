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

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_executable_appointments_types",
            schema = "public",
            joinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    nullable = false),
            inverseJoinColumns = @JoinColumn(
                    name = "appointment_type_id",
                    referencedColumnName = "id",
                    nullable = false))
    private Set<AppointmentType> executableAppointmentTypes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_prescribable_appointments_types",
            schema = "public",
            joinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    nullable = false),
            inverseJoinColumns = @JoinColumn(
                    name = "appointment_type_id",
                    referencedColumnName = "id",
                    nullable = false))
    private Set<AppointmentType> prescribableAppointmentTypes;
}