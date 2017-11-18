package com.epam.hospital.service.implementation;

import com.epam.hospital.dao.api.PatientAppointmentDao;
import com.epam.hospital.model.PatientAppointment;
import com.epam.hospital.model.User;
import com.epam.hospital.service.api.PatientAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.sql.Timestamp;

@Service
public class PatientAppointmentServiceImpl implements PatientAppointmentService {

    @Autowired
    PatientAppointmentDao patientAppointmentDao;

    @Override
    @Transactional
    public boolean fulfil(PatientAppointment appointment, User user) {
        appointment.setFulfilledBy(user);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        appointment.setFulfilledDate(timestamp);
        return patientAppointmentDao.saveOrUpdatePatientAppointment(appointment);
    }

    @Override
    @Transactional
    public boolean appoint(PatientAppointment appointment, User user) {
        appointment.setAppointedBy(user);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        appointment.setAppointedDate(timestamp);
        return patientAppointmentDao.saveOrUpdatePatientAppointment(appointment);
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(PatientAppointment patientAppointment) {
        return patientAppointmentDao.saveOrUpdatePatientAppointment(patientAppointment);
    }

    @Override
    @Transactional
    public PatientAppointment getPatientAppointmentById(int id) {
        return patientAppointmentDao.getPatientAppointmentById(id);
    }

    @Override
    @Transactional
    public boolean deletePatientAppointment(PatientAppointment patientAppointment) {
        return patientAppointmentDao.deletePatientAppointment(patientAppointment);
    }
}
