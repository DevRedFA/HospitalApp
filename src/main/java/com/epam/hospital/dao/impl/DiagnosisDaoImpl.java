package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.DiagnosisDao;

import com.epam.hospital.model.Diagnosis;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DiagnosisDaoImpl implements DiagnosisDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public Diagnosis getDiagnosisById(int id) {
        Diagnosis diagnosis;
        try (Session session = sessionFactory.openSession()) {
            diagnosis = session.get(Diagnosis.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return diagnosis;
    }

    @Transactional
    public List<Diagnosis> getAllDiagnosis() {
        List<Diagnosis> diagnoses;
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Diagnosis ");
            diagnoses = (List<Diagnosis>) query.list();
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return diagnoses;
    }
}
