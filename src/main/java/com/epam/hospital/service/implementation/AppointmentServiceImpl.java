package com.epam.hospital.service.implementation;

import com.epam.hospital.model.PatientAppointment;
import com.epam.hospital.model.User;
import com.epam.hospital.service.api.AppointmentService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Override
    public boolean fulfil(PatientAppointment appointment, User user) {
        appointment.setFulfilledBy(user);
        Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
        appointment.setFulfilledDate(timeNow);
        return true;
    }

    @Override
    public boolean appoint(PatientAppointment appointment, User user) {
        appointment.setAppointedBy(user);
        Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
        appointment.setAppointedDate(timeNow);
        return true;
    }
}
