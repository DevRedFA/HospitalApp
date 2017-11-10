package com.epam.hospital.dao.api;

import com.epam.hospital.model.Diagnosis;

import java.util.List;

public interface DiagnosisDao {
    Diagnosis getDiagnosisById(int id);

    List<Diagnosis> getAllDiagnosis();
}
