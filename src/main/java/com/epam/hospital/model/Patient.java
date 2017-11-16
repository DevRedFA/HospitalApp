package com.epam.hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "patients", schema = "public")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",
            nullable = false)
    private Integer id;


    @Column(name = "name",
            nullable = false, length = -1)
    private String name;


    @Column(name = "surname",
            nullable = false,
            length = -1)
    private String surname;


    @Column(name = "birthdate",
            nullable = false)
    private Date birthdate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Set<PatientAppointment> patientAppointments;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Set<PatientDiagnosis> patientDiagnoses;

}
