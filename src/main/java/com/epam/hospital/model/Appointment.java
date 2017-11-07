package com.epam.hospital.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "appointments", schema = "public")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "appointment_type_id", referencedColumnName = "id")
    private AppointmentType appointmentType;

}
