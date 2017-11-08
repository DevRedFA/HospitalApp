package com.epam.hospital.dao.api;

import com.epam.hospital.model.AppointmentType;

import java.util.List;

public interface AppointmentTypeDao {
    AppointmentType getAppointmentTypeById(int id);

    AppointmentType getAppointmentTypeByName(String name);

    boolean saveAppointmentType(AppointmentType appointmentType);

    boolean updateAppointmentType(AppointmentType appointmentType);

    boolean deleteAppointmentType(AppointmentType appointmentType);

    List<AppointmentType> getAllAppointmentTypes();
}
