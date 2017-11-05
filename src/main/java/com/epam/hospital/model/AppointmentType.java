package com.epam.hospital.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "appointment_types", schema = "public", catalog = "hospitalDB")
public class AppointmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "appointmentTypes")
    private Set<Role> roles;




}
