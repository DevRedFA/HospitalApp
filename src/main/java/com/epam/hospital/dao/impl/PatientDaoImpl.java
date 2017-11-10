package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.PatientDao;
import com.epam.hospital.model.Patient;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class PatientDaoImpl implements PatientDao {

    @Transactional
    public Patient getPatientById(int id) {
        Patient patient = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            patient = (Patient) session.get(Patient.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
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

    @Transactional
    public boolean saveOrUpdatePatient(Patient patient) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(patient);
            transaction.commit();

        } catch (HibernateException hibEx) {
            new RuntimeException(hibEx);
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


    @Override
    public boolean deletePatient(Patient patient) {
        return false;
    }

    @Transactional
    public List<Patient> getAllPatients() {
        Session session = null;
        List<Patient> patients = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Patient");
            patients = (List<Patient>) query.list();
        } catch (HibernateException hibEx) {
            new RuntimeException(hibEx);
        }
        return patients;
    }

    @Transactional
    public List<Patient> getPatientsByRange(int from, int offset) {
        return null;
    }
}
