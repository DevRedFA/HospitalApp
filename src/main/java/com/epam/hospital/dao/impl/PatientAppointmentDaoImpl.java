package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.PatientAppointmentDao;
import com.epam.hospital.model.PatientAppointment;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

public class PatientAppointmentDaoImpl implements PatientAppointmentDao {
    @Transactional
    public boolean saveOrUpdatePatientAppointment(PatientAppointment patientAppointment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(patientAppointment);
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

    @Transactional
    public boolean deletePatientAppointment(PatientAppointment patientAppointment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(patientAppointment);
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
