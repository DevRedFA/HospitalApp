package com.epam.hospital.model;

import lombok.Data;
import lombok.ToString;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@ToString(exclude = {"patient", "appointment", "appointedBy", "fulfilledBy"})
@Table(name = "patients_appointments", schema = "public")
public class PatientAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "appointed_by_id", referencedColumnName = "id", nullable = false)
    private User appointedBy;

    @ManyToOne
    @JoinColumn(name = "fulfilled_by_id", referencedColumnName = "id")
    private User fulfilledBy;

    @Column(name = "appointed_date")
    private LocalDateTime appointedDate;

    @Column(name = "fulfilled_date")
    private LocalDateTime fulfilledDate;

}
