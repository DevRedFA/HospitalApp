package com.epam.hospital.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ToString(exclude = {"prescriberRole", "executorRole"})
@Table(name = "appointments_types", schema = "public")
public class AppointmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "prescribableAppointmentType")
    private List<Role> prescriberRole;

    @ManyToMany(mappedBy = "executableAppointmentType")
    private List<Role> executorRole;

    @OneToMany(mappedBy = "appointmentType")
    private List<Appointment> appointments;

}
