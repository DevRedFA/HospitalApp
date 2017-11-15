package com.epam.hospital.service.implementation;

import com.epam.hospital.dao.api.AppointmentDao;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.service.api.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentDao appointmentDao;

    List<Appointment> allAppointments;

    @Override
    @Transactional
    public Appointment getAppointmentById(int id) {
        return appointmentDao.getAppointmentById(id);
    }

    @Override
    @Transactional
    public List<Appointment> getAllAppointments() {
        if (allAppointments == null) {
            allAppointments = appointmentDao.getAllAppointments();
        }
        return allAppointments;
    }

    @Override
    @Transactional
    public List<Appointment> getAllCommercialAppointments() {
        if (allAppointments == null) {
            getAllAppointments();
        }
        return allAppointments.stream()
                .filter(s -> s.getAppointmentType().getName().equals("Extra service"))
                .collect(Collectors.toList());
    }
}
