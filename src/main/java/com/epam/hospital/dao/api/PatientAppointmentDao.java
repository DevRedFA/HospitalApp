package com.epam.hospital.dao.api;

import com.epam.hospital.model.PatientAppointment;

import java.util.List;

public interface PatientAppointmentDao {
    PatientAppointment getPatientAppointmentById(int id);

    PatientAppointment gePatientAppointmentByName(String name);

    boolean savePatientDiagnosis(PatientAppointment patientAppointment);

    boolean updatePatientDiagnosis(PatientAppointment patientAppointment);

    boolean deletePatientDiagnosis(PatientAppointment patientAppointment);

    List<PatientAppointment> getAllPatientAppointment();
}
