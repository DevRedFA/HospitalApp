package com.epam.hospital.service.implementation;

import com.epam.hospital.dao.api.AppointmentDao;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.service.api.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentDao appointmentDao;

    @Override
    @Transactional
    public Appointment getAppointmentById(int id) {
        return appointmentDao.getAppointmentById(id);
    }
}
