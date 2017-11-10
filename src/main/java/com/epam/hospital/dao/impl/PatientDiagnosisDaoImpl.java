package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.PatientDiagnosisDao;
import com.epam.hospital.model.PatientDiagnosis;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PatientDiagnosisDaoImpl implements PatientDiagnosisDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public PatientDiagnosis getPatientDiagnosisById(int id) {
        PatientDiagnosis patientDiagnosis = null;
        try (Session session = sessionFactory.openSession()) {
            patientDiagnosis = session.get(PatientDiagnosis.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return patientDiagnosis;
    }


    @Transactional
    public boolean saveOrUpdatePatientDiagnosis(PatientDiagnosis patientDiagnosis) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(patientDiagnosis);
            transaction.commit();

        } catch (HibernateException hibEx) {
            transaction.rollback();
            throw new RuntimeException(hibEx);
        }
        return true;
    }

    @Transactional
    public boolean deletePatientDiagnosis(PatientDiagnosis patientDiagnosis) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(patientDiagnosis);
            transaction.commit();
        } catch (HibernateException hibEx) {
            transaction.rollback();
            throw new RuntimeException(hibEx);
        }
        return true;
    }
}
