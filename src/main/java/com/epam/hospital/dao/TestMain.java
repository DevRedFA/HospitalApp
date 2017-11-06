package com.epam.hospital.dao;

import com.epam.hospital.model.AppointmentType;
import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Set;

public class TestMain {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();




    }
}
