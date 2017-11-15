package com.epam.hospital.service.api;

import com.epam.hospital.model.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment getAppointmentById(int id);

    List<Appointment> getAllAppointments();

    List<Appointment> getAllCommercialAppointments();
}

