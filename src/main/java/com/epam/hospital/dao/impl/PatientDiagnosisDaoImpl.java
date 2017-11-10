package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.PatientDiagnosisDao;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

public class PatientDiagnosisDaoImpl implements PatientDiagnosisDao {

    @Transactional
    public boolean saveOrUpdatePatientDiagnosis(PatientDiagnosis patientDiagnosis) {
        Session session = null;
        Transaction trans = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            trans = session.beginTransaction();
            session.saveOrUpdate(patientDiagnosis);
            trans.commit();

        } catch (HibernateException hibEx) {
            new RuntimeException(hibEx);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            if (trans != null && !trans.wasCommitted()) {
                trans.rollback();
            }
        }

        return true;
    }

    @Transactional
    public boolean deletePatientDiagnosis(PatientDiagnosis patientDiagnosis) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(patientDiagnosis);
            transaction.commit();
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            if (transaction != null && !transaction.wasCommitted()) {
                transaction.rollback();
            }
        }
        return true;
    }
}
