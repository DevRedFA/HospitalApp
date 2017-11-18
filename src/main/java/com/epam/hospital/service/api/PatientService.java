package com.epam.hospital.service.api;

import com.epam.hospital.model.Patient;

import java.util.List;

public interface PatientService {

    Patient getPatientById(int id);

    List<Patient> getAllPatients();

    List<Patient> getFirstPartOfPatients();

    List<Patient> getNextPartOfPatients();

    List<Patient> getPreviousPartOfPatients();

    List<Patient> updatePartOfPatients();

    boolean isPreviousPageAvailable();

    boolean isNextPageAvailable();

    boolean saveOrUpdatePatient(Patient patient);

    boolean deletePatient(Patient patient);
}
