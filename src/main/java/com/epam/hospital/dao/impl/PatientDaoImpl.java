package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.PatientDao;
import com.epam.hospital.model.Patient;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

@Repository
public class PatientDaoImpl implements PatientDao {

    @Autowired
    private SessionFactory sessionFactory;


    public Patient getPatientById(int id) {
        Patient patient = null;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            patient = session.get(Patient.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return patient;
    }

    @Override
    public Patient getByName(String name) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Patient getPatientbyUserId(int id) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Patient getBySurname(String surname) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Patient getByBirthDate(Date date) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }


    public boolean saveOrUpdatePatient(Patient patient) {

        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(patient);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return true;
    }


    @Override
    public boolean deletePatient(Patient patient) {
        throw new UnsupportedOperationException();
    }


    public List<Patient> getAllPatients() {
        List<Patient> patients = null;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Patient");
            patients = (List<Patient>) query.list();
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return patients;
    }

    public List<Patient> getPatientsByRange(int from, int offset) {
        List<Patient> patients = null;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            Query limitPatients = session.createQuery("FROM Patient ORDER BY id");
            limitPatients.setFirstResult(from);
            limitPatients.setMaxResults(offset);
            patients = (List<Patient>) limitPatients.list();
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return patients;
    }
}
