package com.epam.hospital.service.implementation;

import com.epam.hospital.dao.api.PatientDiagnosisDao;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.service.api.PatientDiagnosesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientDiagnosesServiceImpl implements PatientDiagnosesService {

    @Autowired
    PatientDiagnosisDao patientDiagnosisDao;

    @Override
    @Transactional
    public boolean saveOrUpdate(PatientDiagnosis diagnosis) {
        return patientDiagnosisDao.saveOrUpdatePatientDiagnosis(diagnosis);
    }

    @Override
    @Transactional
    public PatientDiagnosis getPatientDiagnosisById(int id) {
        return patientDiagnosisDao.getPatientDiagnosisById(id);
    }
}
