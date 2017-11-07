package com.epam.hospital.model;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "patient_appointments", schema = "public")
public class PatientAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;

    @OneToOne
    @JoinColumn(name = "appointed_by_id", referencedColumnName = "id")
    private User appointedBy;

    @OneToOne
    @JoinColumn(name = "fulfilled_by_id", referencedColumnName = "id")
    private User fulfilledBy;

    @Column(name = "appointed_date")
    private LocalDateTime appointedDate;

    @Column(name = "fulfilled_date")
    private LocalDateTime fulfilledDate;


}
