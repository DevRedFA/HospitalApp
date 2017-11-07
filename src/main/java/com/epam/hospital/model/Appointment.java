package com.epam.hospital.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(exclude = "appointmentType")
@Entity
@Table(name = "appointments", schema = "public")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "appointment", fetch = FetchType.EAGER)
    private List<PatientAppointment> patientAppointments;

    @ManyToOne
    @JoinColumn(name = "appointment_type_id", referencedColumnName = "id", nullable = false)
    private AppointmentType appointmentType;

}
