package com.epam.hospital.dao.api;

import com.epam.hospital.model.Appointment;

import java.util.List;

public interface AppointmentDao {
    Appointment getAppointmentById(int id);

    Appointment getAppointmentByName(String name);

    boolean saveAppointment(Appointment appointment);

    boolean updateAppointment(Appointment appointment);

    boolean deleteAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    //09.11
    List<Appointment> getExtraServices();
}
