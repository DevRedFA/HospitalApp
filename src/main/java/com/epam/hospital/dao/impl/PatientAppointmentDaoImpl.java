package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.PatientAppointmentDao;
import com.epam.hospital.model.PatientAppointment;
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
public class PatientAppointmentDaoImpl implements PatientAppointmentDao {

    @Autowired
    private SessionFactory sessionFactory;


    public PatientAppointment getPatientAppointmentById(int id) {
        PatientAppointment patientAppointment = null;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            patientAppointment = session.get(PatientAppointment.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return patientAppointment;
    }

    public boolean saveOrUpdatePatientAppointment(PatientAppointment patientAppointment) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(patientAppointment);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return true;
    }

    @Transactional
    public boolean deletePatientAppointment(PatientAppointment patientAppointment) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.delete(patientAppointment);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return true;
    }
}
