package com.epam.hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@ToString(exclude = {"diagnosis", "patient", "diagnosedBy"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "patients_diagnoses", schema = "public", catalog = "hospitalDB")
public class PatientDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Integer id;

    @Column(name = "diagnosed_date",
            nullable = false)
    private Timestamp diagnosedDate;

    @Column(name = "discharge",
            nullable = false)
    @ColumnDefault("false")
    private Boolean discharge;

    @Column(name = "details",
            nullable = true,
            length = -1)
    private String details;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id",
            referencedColumnName = "id",
            nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diagnosis_id",
            referencedColumnName = "id",
            nullable = false)
    private Diagnosis diagnosis;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diagnosed_by",
            referencedColumnName = "id",
            nullable = false)
    private User diagnosedBy;

}
