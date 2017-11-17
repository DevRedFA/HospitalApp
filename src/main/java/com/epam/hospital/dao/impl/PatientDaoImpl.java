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
        Session session = sessionFactory.getCurrentSession();
        Patient patient = session.get(Patient.class, id);
        return patient;
    }

    public boolean saveOrUpdatePatient(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(patient);
        return true;
    }

    public boolean deletePatient(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(patient);
        return true;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Patient");
        patients = (List<Patient>) query.list();
        return patients;
    }

    public List<Patient> getPatientsByRange(int from, int offset) {
        List<Patient> patients;
        Session session = sessionFactory.getCurrentSession();
        Query limitPatients = session.createQuery("FROM Patient ORDER BY id");
        limitPatients.setFirstResult(from);
        limitPatients.setMaxResults(offset);
        patients = (List<Patient>) limitPatients.list();
        return patients;
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
}
