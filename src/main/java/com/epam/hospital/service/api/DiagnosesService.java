package com.epam.hospital.service.api;

import com.epam.hospital.model.PatientDiagnosis;

public interface DiagnosesService {
    static String getFormattedView(PatientDiagnosis diagnosis) {
        StringBuilder sb = new StringBuilder();
        sb.append("Diagnosis: ")
                .append(diagnosis.getDiagnosis() != null ? diagnosis.getDiagnosis().getName() : "")
                .append(System.lineSeparator()).append("Diagnosed by: ")
                .append(diagnosis.getDiagnosedBy().getUsername() != null ? diagnosis.getDiagnosedBy().getUsername() : "")
                .append(System.lineSeparator())
                .append("Diagnosed date: ")
                .append(diagnosis.getDiagnosedDate() != null ? diagnosis.getDiagnosedDate() : "")
                .append(System.lineSeparator())
                .append("Details: ")
                .append(diagnosis.getDetails() != null ? diagnosis.getDetails() : "")
                .append(System.lineSeparator())
                .append("Is discharge: ")
                .append(diagnosis.getDischarge() != null ? diagnosis.getDischarge() : "false")
                .append(System.lineSeparator());
        return sb.toString();
    }
}
