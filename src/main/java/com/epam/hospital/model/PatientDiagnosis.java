package com.epam.hospital.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@ToString(exclude = "diagnosis")
@Entity
@Table(name = "patients_diagnoses", schema = "public", catalog = "hospitalDB")
public class PatientDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "diagnosis_id", referencedColumnName = "id", nullable = false)
    private Diagnosis diagnosis;

    @Column(name = "diagnosed_date")
    private LocalDateTime diagnosedDate;

    @Column(name = "discharge")
    private boolean discharge;

    @ManyToOne
    @JoinColumn(name = "diagnosed_by", referencedColumnName = "id", nullable = false)
    private User diagnosedBy;

}
