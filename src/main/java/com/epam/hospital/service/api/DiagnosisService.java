package com.epam.hospital.service.api;

import com.epam.hospital.model.Diagnosis;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.model.User;

import java.util.List;

public interface DiagnosisService {
    Diagnosis getDiagnosisById(int id);

    List<Diagnosis> getAllDiagnosis();

}
