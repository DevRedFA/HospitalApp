package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.DiagnosisDao;

import com.epam.hospital.model.Diagnosis;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DiagnosisDaoImpl implements DiagnosisDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Diagnosis getDiagnosisById(int id) {
        Diagnosis diagnosis;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            diagnosis = session.get(Diagnosis.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return diagnosis;
    }

    public List<Diagnosis> getAllDiagnosis() {
        List<Diagnosis> diagnoses;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Diagnosis ");
            diagnoses = (List<Diagnosis>) query.list();
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return diagnoses;
    }
}
