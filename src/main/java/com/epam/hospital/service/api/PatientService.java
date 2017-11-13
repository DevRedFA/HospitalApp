package com.epam.hospital.service.api;

import com.epam.hospital.model.Patient;

import java.util.List;

public interface PatientService {

    Patient getPatientById(int id);


    List<Patient> getNextPartOfPatients();

    List<Patient> getPreviousPartOfPatients();
}
