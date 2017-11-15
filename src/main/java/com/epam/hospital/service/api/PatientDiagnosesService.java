package com.epam.hospital.service.api;

import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.model.User;

public interface PatientDiagnosesService {
    boolean saveOrUpdate(PatientDiagnosis diagnosis);

    PatientDiagnosis getPatientDiagnosisById(int id);

    boolean discharge(PatientDiagnosis patientDiagnosis);
}
