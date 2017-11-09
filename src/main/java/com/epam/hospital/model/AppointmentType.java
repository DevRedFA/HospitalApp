package com.epam.hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "appointments_types", schema = "public")
public class AppointmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Integer id;


    @Column(name = "name",
            nullable = false,
            length = -1)
    private String name;

    @OneToMany(mappedBy = "appointmentType", fetch = FetchType.EAGER)
    private Set<Appointment> appointments;

    @ManyToMany(mappedBy = "executableAppointmentTypes", fetch = FetchType.EAGER)
    private Set<Role> executorRoles;

    @ManyToMany(mappedBy = "prescribableAppointmentTypes", fetch = FetchType.EAGER)
    private Set<Role> prescriberRoles;
}
