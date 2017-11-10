package com.epam.hospital.dao.api;

import com.epam.hospital.model.PatientDiagnosis;

import java.util.List;

public interface PatientDiagnosisDao {

    boolean saveOrUpdatePatientDiagnosis(PatientDiagnosis patientDiagnosis);

    boolean deletePatientDiagnosis(PatientDiagnosis patientDiagnosis);

}
