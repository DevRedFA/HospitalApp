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
        Session session = sessionFactory.getCurrentSession();
        Diagnosis diagnosis = session.get(Diagnosis.class, id);
        return diagnosis;
    }

    public List<Diagnosis> getAllDiagnosis() {
        List<Diagnosis> diagnoses;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Diagnosis ");
        diagnoses = (List<Diagnosis>) query.list();
        return diagnoses;
    }
}
