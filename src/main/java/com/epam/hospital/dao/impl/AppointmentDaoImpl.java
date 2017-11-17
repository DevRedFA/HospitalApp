package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.AppointmentDao;
import com.epam.hospital.model.Appointment;
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
public class AppointmentDaoImpl implements AppointmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Appointment getAppointmentById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Appointment appointment = session.get(Appointment.class, id);
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments;
        Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Appointment ");
            appointments = (List<Appointment>) query.list();
        return appointments;
    }

    @Override
    public List<Appointment> getAllCommercialAppointments() {
        throw new UnsupportedOperationException();
    }
}
