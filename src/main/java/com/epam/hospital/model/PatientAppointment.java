package com.epam.hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@ToString(exclude = {"patient", "appointment", "appointedBy", "fulfilledBy"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "patients_appointments", schema = "public")
public class PatientAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",
            nullable = false)
    private Integer id;


    @Column(name = "appointed_date",
            nullable = false)
    private Timestamp appointedDate;


    @Column(name = "fulfilled_date")
    private Timestamp fulfilledDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id",
            referencedColumnName = "id",
            nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id",
            referencedColumnName = "id",
            nullable = false)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointed_by_id",
            referencedColumnName = "id",
            nullable = false)
    private User appointedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fulfilled_by_id",
            referencedColumnName = "id")
    private User fulfilledBy;

}
