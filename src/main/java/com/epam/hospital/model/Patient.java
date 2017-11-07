package com.epam.hospital.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "patients", schema = "public")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthdate")
    private Date birthdate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
