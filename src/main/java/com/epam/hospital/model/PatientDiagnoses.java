package com.epam.hospital.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "patient_diagnoses")
public class PatientDiagnoses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "diagnosis_id", referencedColumnName = "id")
    private Diagnose diagnose;

    @Column(name = "diagnosis_date")
    private LocalDateTime diagnosisDate;

    @Column(name = "discharge")
    private boolean discharge;

}
