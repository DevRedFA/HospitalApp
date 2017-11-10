package com.epam.hospital.dao.api;

import com.epam.hospital.model.PatientAppointment;

import java.util.List;

public interface PatientAppointmentDao {

    PatientAppointment getPatientAppointmentById(int id);

    boolean saveOrUpdatePatientAppointment(PatientAppointment patientAppointment);

    boolean deletePatientAppointment(PatientAppointment patientAppointment);

}
