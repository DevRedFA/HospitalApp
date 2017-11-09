package com.epam.hospital;

import com.epam.hospital.dao.impl.UserDaoImpl;
import com.epam.hospital.model.*;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.el.ExpressionFactory;

public class TestMain {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        UserDaoImpl userDao = new UserDaoImpl();

//        System.out.println("Test Nurse: " + userDao.getUserByName("Test Nurse"));
//        System.out.println("Test Doctor: " + userDao.getUserByName("Test Doctor"));
//        System.out.println("Test Absent: " + userDao.getUserByName("Test Absent"));
//
//        System.out.println("5Test Nurse: " + userDao.getUserByName5("Test Nurse"));
//        System.out.println("5Test Doctor: " + userDao.getUserByName5("Test Doctor"));
//        System.out.println("5Test Absent: " + userDao.getUserByName5("Test Absent"));
//
//        User user = userDao.getUserById(1);
//        System.out.println("User id 1: " + user);
//
//        Role role = session.get(Role.class, 1);
//        System.out.println("Role 1: "+ role);
//
//        PatientDiagnosis patientDiagnosis = session.get(PatientDiagnosis.class, 1);
//        System.out.println("PatientDiagnosis 1: "+ patientDiagnosis);
//
//        Patient patient = session.get(Patient.class, 1);
//        System.out.println("Patient 1: "+ patient);
//
//        PatientAppointment patientAppointment = session.get(PatientAppointment.class, 2);
//        System.out.println("PatientAppointment 2: "+ patientAppointment);
//
//        Diagnosis diagnosis = session.get(Diagnosis.class, 1);
//        System.out.println("Diagnosis 1: "+ diagnosis);
//
//        Appointment appointment = session.get(Appointment.class, 1);
//        System.out.println("Appointment 1: "+ appointment);
//
//        AppointmentType appointmentType = session.get(AppointmentType.class, 1);
//        System.out.println("AppointmentType 1: "+ appointmentType);
//        System.out.println();

        //getting all users
        System.out.println("getting all users:");
        System.out.println(userDao.getAll());
        System.out.println();

        //add User:
        User karlito = new User();
        karlito.setUsername("karlito");
        karlito.setPassword("karlito");
        userDao.saveUser(karlito);
        System.out.println("user karlito added.");
        System.out.println(karlito);
        System.out.println();

        System.out.println("getting user karlito from database:");
        User karlito1 = userDao.getUserByName("karlito");
        System.out.println(karlito1);
        System.out.println();

        //update User:
        karlito1.setUsername("karlito-updated");
        userDao.updateUser(karlito1);
        System.out.println("user karlito updated.");
        System.out.println();

        System.out.println("getting user karlito-updated from database:");
        User karlito2 = userDao.getUserByName("karlito-updated");
        System.out.println(karlito2);
        System.out.println();

        //delete User:
        userDao.deleteUser(karlito2);
        System.out.println("user karlito-updated deleted.");
        System.out.println();

        System.out.println("trying to get deleted user from db:");
        System.out.println(userDao.getUserByName("karlito-updated"));
        System.out.println();

        session.close();
        sessionFactory.close();

    }
}
