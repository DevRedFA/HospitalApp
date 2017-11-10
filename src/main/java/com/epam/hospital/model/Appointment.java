package com.epam.hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@ToString(exclude = "appointmentType")
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "appointments", schema = "public")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Integer id;


    @Column(name = "name", nullable = false, length = -1)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_type_id",
            referencedColumnName = "id",
            nullable = false)
    private AppointmentType appointmentType;

    @OneToMany(mappedBy = "appointment", fetch = FetchType.EAGER)
    private Set<PatientAppointment> patientAppointments;
}
