package com.epam.hospital.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@ToString(exclude = "roles")
@Table(name = "users", schema = "public", catalog = "hospitalDB")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Patient patient;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "appointedBy", fetch = FetchType.EAGER)
    private List<PatientAppointment> prescribedPatientAppointments;

    @OneToMany(mappedBy = "fulfilledBy", fetch = FetchType.EAGER)
    private List<PatientAppointment> fulfilledPatientAppointments;

    @OneToMany(mappedBy = "diagnosedBy", fetch = FetchType.EAGER)
    private List<PatientDiagnosis> patientsDiagnoses;
}
