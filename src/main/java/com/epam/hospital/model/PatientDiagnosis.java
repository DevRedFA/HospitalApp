package com.epam.hospital.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@ToString(exclude = {"diagnosis", "patient", "diagnosedBy"})
@Entity
@Table(name = "patients_diagnoses", schema = "public")
public class PatientDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id",
            referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "diagnosis_id",
            referencedColumnName = "id",
            nullable = false)
    private Diagnosis diagnosis;

    @Column(name = "diagnosed_date")
    private Date diagnosedDate;

    @Column(name = "discharge")
    private boolean discharge;

    @ManyToOne
    @JoinColumn(name = "diagnosed_by",
            referencedColumnName = "id",
            nullable = false)
    private User diagnosedBy;

}
