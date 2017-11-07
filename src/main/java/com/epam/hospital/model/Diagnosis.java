package com.epam.hospital.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "diagnoses", schema = "public")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "diagnosis", fetch = FetchType.EAGER)
    private List<PatientDiagnosis> patientDiagnoses;
}
