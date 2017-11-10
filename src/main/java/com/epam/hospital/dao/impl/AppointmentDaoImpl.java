package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.AppointmentDao;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public class AppointmentDaoImpl implements AppointmentDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public Appointment getAppointmentById(int id) {
        Appointment appointment = null;
        try (Session session = sessionFactory.openSession()) {
            appointment = session.get(Appointment.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return appointment;
    }

    @Transactional
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments;
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Appointment ");
            appointments = (List<Appointment>) query.list();
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return appointments;

    }

    @Override
    public List<Appointment> getAllCommercialAppointments() {
        throw new UnsupportedOperationException();
    }
}
