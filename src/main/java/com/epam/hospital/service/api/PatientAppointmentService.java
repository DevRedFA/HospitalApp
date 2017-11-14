package com.epam.hospital.service.api;

import com.epam.hospital.model.PatientAppointment;
import com.epam.hospital.model.User;

public interface PatientAppointmentService {

    boolean fulfil(PatientAppointment appointment, User user);

    boolean appoint(PatientAppointment appointment, User user);

    boolean saveOrUpdate(PatientAppointment appointment);

    PatientAppointment getPatientAppointmentById(int id);

    static String getFormattedView(PatientAppointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment: ")
                .append(appointment.getAppointment().getName() != null ? appointment.getAppointment().getName() : "")
                .append(System.lineSeparator()).append("Appointed by: ")
                .append(appointment.getAppointedBy().getUsername() != null ? appointment.getAppointedBy().getUsername() : "")
                .append(System.lineSeparator())
                .append("Appointed date: ")
                .append(appointment.getAppointedDate() != null ? appointment.getAppointedDate() : "")
                .append(System.lineSeparator())
                .append("Appointment type: ")
                .append(appointment.getAppointment().getAppointmentType() != null ? appointment.getAppointment().getAppointmentType().getName() : "")
                .append(System.lineSeparator())
                .append("Fulfilled by: ")
                .append(appointment.getFulfilledBy() != null ? appointment.getFulfilledBy().getUsername() : "")
                .append(System.lineSeparator())
                .append("Fulfilled date: ")
                .append(appointment.getFulfilledDate() != null ? appointment.getFulfilledDate() : "")
                .append(System.lineSeparator());
        return sb.toString();
    }
}

