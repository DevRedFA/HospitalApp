package com.epam.hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "diagnoses", schema = "public")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Integer id;


    @Column(name = "name",
            nullable = false,
            length = -1)
    private String name;

    @OneToMany(mappedBy = "diagnosis", fetch = FetchType.EAGER)
    private Set<PatientDiagnosis> patientDiagnoses;

}
