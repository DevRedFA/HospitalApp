package com.epam.hospital.service.implementation;

import com.epam.hospital.dao.api.PatientDao;
import com.epam.hospital.dao.impl.PatientDaoImpl;
import com.epam.hospital.model.Patient;
import com.epam.hospital.service.api.PatientService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientDao patientDao = new PatientDaoImpl();

    List<Patient> allPatients;
    int currentPos = 0;
    int step = 10;

    @Override
    public Patient getPatientById(int id) {
        return patientDao.getPatientById(id);
    }

    public void init() {
        allPatients = patientDao.getAllPatients();
    }


    @Override
    public List<Patient> getNextPartOfPatients() {
        List<Patient> patients;
        if (currentPos + step <= allPatients.size()) {
            patients = allPatients.subList(currentPos, currentPos + step);
            currentPos += step;
        } else {
            patients = allPatients.subList(currentPos, allPatients.size());
            currentPos = allPatients.size();
        }
        return patients;
    }

    @Override
    public List<Patient> getPreviousPartOfPatients() {
        List<Patient> patients;
        if (currentPos - step >= 0) {
            patients = allPatients.subList(currentPos - step, currentPos);
            currentPos -= step;
        } else {
            patients = allPatients.subList(0, currentPos);
            currentPos = 0;
        }
        return patients;
    }
}
