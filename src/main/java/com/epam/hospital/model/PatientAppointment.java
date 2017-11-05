package com.epam.hospital.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "patient_appointments", schema = "public", catalog = "hospitalDB")
public class PatientAppointment {
    private int id;
    private Timestamp appointedDate;
    private Timestamp fulfilledDate;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "appointed_date")
    public Timestamp getAppointedDate() {
        return appointedDate;
    }

    public void setAppointedDate(Timestamp appointedDate) {
        this.appointedDate = appointedDate;
    }

    @Basic
    @Column(name = "fulfilled_date")
    public Timestamp getFulfilledDate() {
        return fulfilledDate;
    }

    public void setFulfilledDate(Timestamp fulfilledDate) {
        this.fulfilledDate = fulfilledDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientAppointment that = (PatientAppointment) o;

        if (id != that.id) return false;
        if (appointedDate != null ? !appointedDate.equals(that.appointedDate) : that.appointedDate != null)
            return false;
        if (fulfilledDate != null ? !fulfilledDate.equals(that.fulfilledDate) : that.fulfilledDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (appointedDate != null ? appointedDate.hashCode() : 0);
        result = 31 * result + (fulfilledDate != null ? fulfilledDate.hashCode() : 0);
        return result;
    }
}
