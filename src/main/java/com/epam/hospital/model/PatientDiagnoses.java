package com.epam.hospital.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "patient_diagnoses", schema = "public", catalog = "hospitalDB")
public class PatientDiagnoses {
    private int id;
    private Timestamp diagnosisDate;
    private boolean discharge;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "diagnosis_date")
    public Timestamp getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Timestamp diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    @Basic
    @Column(name = "discharge")
    public boolean isDischarge() {
        return discharge;
    }

    public void setDischarge(boolean discharge) {
        this.discharge = discharge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientDiagnoses that = (PatientDiagnoses) o;

        if (id != that.id) return false;
        if (discharge != that.discharge) return false;
        if (diagnosisDate != null ? !diagnosisDate.equals(that.diagnosisDate) : that.diagnosisDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (diagnosisDate != null ? diagnosisDate.hashCode() : 0);
        result = 31 * result + (discharge ? 1 : 0);
        return result;
    }
}
