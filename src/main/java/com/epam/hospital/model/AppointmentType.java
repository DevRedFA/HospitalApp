package com.epam.hospital.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "appointment_types", schema = "public")
public class AppointmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "prescribableAppointmentType")
    private Set<Role> prescriberRole;

    @ManyToMany(mappedBy = "executableAppointmentType")
    private Set<Role> executorRole;

}
