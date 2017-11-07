package com.epam.hospital.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "diagnoses", schema = "public")
public class Diagnose {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

}
