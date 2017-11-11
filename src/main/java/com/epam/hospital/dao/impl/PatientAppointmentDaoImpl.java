package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.PatientAppointmentDao;
import com.epam.hospital.model.PatientAppointment;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PatientAppointmentDaoImpl implements PatientAppointmentDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public PatientAppointment getPatientAppointmentById(int id) {
        PatientAppointment patientAppointment = null;
        try (Session session = sessionFactory.openSession()) {
            patientAppointment = session.get(PatientAppointment.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return patientAppointment;
    }

    @Transactional
    public boolean saveOrUpdatePatientAppointment(PatientAppointment patientAppointment) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(patientAppointment);
            transaction.commit();

        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return true;
    }

    @Transactional
    public boolean deletePatientAppointment(PatientAppointment patientAppointment) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(patientAppointment);
            transaction.commit();
        } catch (HibernateException hibEx) {
            transaction.rollback();
            throw new RuntimeException(hibEx);
        }
        return true;
    }
}