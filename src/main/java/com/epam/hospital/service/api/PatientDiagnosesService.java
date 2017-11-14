package com.epam.hospital.service.api;

import com.epam.hospital.model.PatientDiagnosis;

public interface PatientDiagnosesService {
    boolean saveOrUpdate(PatientDiagnosis diagnosis);

    PatientDiagnosis getPatientDiagnosisById(int id);

}
