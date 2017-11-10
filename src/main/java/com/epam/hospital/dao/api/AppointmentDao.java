package com.epam.hospital.dao.api;

import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Diagnosis;

import java.util.List;

public interface AppointmentDao {

    Appointment getAppointmentById(int id);

    List<Appointment> getAllAppointments();

    List<Appointment> getAllCommercialAppointments();
}
