package com.epam.hospital.service.api;

import com.epam.hospital.model.PatientAppointment;
import com.epam.hospital.model.User;

public interface AppointmentService {

    boolean fulfil(PatientAppointment appointment, User user);

    boolean appoint(PatientAppointment appointment, User user);
}

