package com.epam.hospital.dao.api;

import com.epam.hospital.model.Patient;

import java.util.Date;
import java.util.List;

/**
 * I save as much method as I think it should be so you can choose.
 */
public interface PatientDao {
    Patient getPatientById(int id);
    Patient getByName(String name);
    //09.11
    Patient getPatientbyUserId(int id);

    Patient getBySurname(String surname);

    Patient getByBirthDate(Date date);

    boolean saveOrUpdatePatient(Patient patient);

    boolean deletePatient(Patient patient);

    List<Patient> getAllPatients();

    /**
     * Could be helpful for paging.
     */
    List<Patient> getPatientsByRange(int from, int offset);

}
