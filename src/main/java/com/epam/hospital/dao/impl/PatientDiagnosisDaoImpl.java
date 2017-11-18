package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.PatientDiagnosisDao;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PatientDiagnosisDaoImpl implements PatientDiagnosisDao {

    @Autowired
    private SessionFactory sessionFactory;

    public PatientDiagnosis getPatientDiagnosisById(int id) {
        Session session = sessionFactory.getCurrentSession();
        PatientDiagnosis patientDiagnosis = session.get(PatientDiagnosis.class, id);
        return patientDiagnosis;
    }

    public boolean saveOrUpdatePatientDiagnosis(PatientDiagnosis patientDiagnosis) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(patientDiagnosis);
        return true;
    }

    public boolean deletePatientDiagnosis(PatientDiagnosis patientDiagnosis) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(patientDiagnosis);
        return true;
    }
}
