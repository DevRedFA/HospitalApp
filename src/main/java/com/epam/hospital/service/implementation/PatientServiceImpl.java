package com.epam.hospital.service.implementation;

import com.epam.hospital.dao.api.PatientDao;
import com.epam.hospital.dao.impl.PatientDaoImpl;
import com.epam.hospital.model.Patient;
import com.epam.hospital.service.api.PatientService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Setter
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientDao patientDao;

    boolean previousPageAvailable = false;
    boolean nextPageAvailable = true;
    List<Patient> allPatients;
    // Always show last id of shown patients
    int currentPos = 0;
    int step = 16;
    int size = 0;

    @Override
    @Transactional
    public Patient getPatientById(int id) {
        return patientDao.getPatientById(id);
    }

    @Override
    @Transactional
    public List<Patient> getFirstPartOfPatients() {
        currentPos += step;
        return patientDao.getPatientsByRange(0, step);
    }


    @Override
    @Transactional
    public List<Patient> getNextPartOfPatients() {
        List<Patient> patients;
        if (allPatients == null) {
            allPatients = patientDao.getAllPatients();
        }
        if (currentPos + step <= allPatients.size()) {
            patients = patientDao.getPatientsByRange(currentPos, step);
            currentPos += step;
        } else {
            patients = patientDao.getPatientsByRange(currentPos, allPatients.size() - currentPos);
            currentPos = allPatients.size();
            nextPageAvailable = false;
        }
        previousPageAvailable = true;
        return patients;
    }

    @Override
    @Transactional
    public List<Patient> getPreviousPartOfPatients() {
        List<Patient> patients;
        if (currentPos - step > 0) {
            if (currentPos % step != 0) {
                patients = patientDao.getPatientsByRange(currentPos - currentPos % step - step, step);
                currentPos -= currentPos % step;
            } else {
                patients = patientDao.getPatientsByRange(currentPos - 2 * step, step);
                currentPos -= step;
            }
        } else {
            patients = patientDao.getPatientsByRange(0, currentPos);
            currentPos = 10;
            previousPageAvailable = false;
        }
        nextPageAvailable = true;
        return patients;
    }

    @Override
    public boolean isPreviousPageAvailable() {
        return previousPageAvailable;
    }

    @Override
    public boolean isNextPageAvailable() {
        return nextPageAvailable;
    }

    @Override
    @Transactional
    public boolean saveOrUpdatePatient(Patient patient) {
        return patientDao.saveOrUpdatePatient(patient);
    }
}
