package com.epam.hospital.dao.api;

import com.epam.hospital.model.PatientDiagnosis;

import java.util.List;

public interface PatientDiagnosisDao {
    PatientDiagnosis getPatientDiagnosisById(int id);

    PatientDiagnosis gePatientDiagnosisByName(String name);

    boolean savePatientDiagnosis(PatientDiagnosis patientDiagnosis);

    boolean updatePatientDiagnosis(PatientDiagnosis patientDiagnosis);

    boolean deletePatientDiagnosis(PatientDiagnosis patientDiagnosis);

    List<PatientDiagnosis> getAllPatientDiagnosis();
}
