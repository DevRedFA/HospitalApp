package com.epam.hospital;

import com.epam.hospital.dao.api.AppointmentDao;
import com.epam.hospital.dao.api.PatientAppointmentDao;
import com.epam.hospital.dao.api.PatientDiagnosisDao;
import com.epam.hospital.dao.api.UserDao;
import com.epam.hospital.dao.impl.*;
import com.epam.hospital.model.*;
import com.epam.hospital.util.HibernateUtil;

import java.sql.Date;
import java.sql.Timestamp;

public class TestMain {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoImpl();
        User user = userDao.getUserById(2);
        PatientAppointment patientAppointment = new PatientAppointment();
        Patient patient = new PatientDaoImpl().getPatientById(1);
        patientAppointment.setAppointedBy(user);
        patientAppointment.setPatient(patient);
        AppointmentDao appointmentDao = new AppointmentDaoImpl();
        Appointment appointmentById = appointmentDao.getAppointmentById(1);
        System.out.println(appointmentById.getName());
        patientAppointment.setAppointment(appointmentById);
        patientAppointment.setAppointedDate(new Timestamp(System.currentTimeMillis()));

        PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDaoImpl();
        patientAppointmentDao.saveOrUpdatePatientAppointment(patientAppointment);


        HibernateUtil.getSessionFactory().close();
    }
}
