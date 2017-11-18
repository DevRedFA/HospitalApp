package com.epam.hospital;

import com.epam.hospital.dao.api.*;
import com.epam.hospital.dao.impl.*;
import com.epam.hospital.model.*;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.service.implementation.PatientServiceImpl;
import com.epam.hospital.util.HibernateUtil;
import lombok.extern.log4j.Log4j;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
@Log4j
public class TestMain {
    public static void main(String[] args) {

//        UserDao userDao = new UserDaoImpl();
//        User user = userDao.getUserById(2);
//        PatientAppointment patientAppointment = new PatientAppointment();
//        Patient patient = new PatientDaoImpl().getPatientById(1);
//        patientAppointment.setAppointedBy(user);
//        patientAppointment.setPatient(patient);
//        AppointmentDao appointmentDao = new AppointmentDaoImpl();
//        Appointment appointmentById = appointmentDao.getAppointmentById(1);
//        System.out.println(appointmentById.getName());
//        patientAppointment.setAppointment(appointmentById);
//        patientAppointment.setAppointedDate(new Timestamp(System.currentTimeMillis()));
//
//        PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDaoImpl();
//        patientAppointmentDao.saveOrUpdatePatientAppointment(patientAppointment);
//        RoleDao roleDao = new RoleDaoImpl();
//        Role roleById = roleDao.getRoleById(1);
//        System.out.println("Role by id = " + roleById.getName());
//
//        Role role_patient = roleDao.getRoleByName("ROLE_PATIENT");
//
//        System.out.println("Role by name = " + role_patient.getName() + " " + role_patient.getId());

//        PatientService patientService = new PatientServiceImpl();
//        List<Patient> nextPartOfPatients = patientService.getNextPartOfPatients();

//        System.out.println(nextPartOfPatients);
//        HibernateUtil.getSessionFactory().close();
    }
}