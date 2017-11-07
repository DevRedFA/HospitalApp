package com.epam.hospital.dao.api;

import com.epam.hospital.model.Diagnosis;

import java.util.List;

public interface DiagnosisDao {
    Diagnosis getDiagnosisById(int id);

    Diagnosis getDiagnosisByName(String name);

    boolean saveDiagnosis(Diagnosis diagnosis);

    boolean updateDiagnosis(Diagnosis diagnosis);

    boolean deleteAppointment(Diagnosis diagnosis);

    List<Diagnosis> getAllDiagnosis();
}
