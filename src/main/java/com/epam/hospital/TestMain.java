package com.epam.hospital;

import com.epam.hospital.dao.impl.UserDaoImpl;
import com.epam.hospital.model.User;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TestMain {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        UserDaoImpl userDao = new UserDaoImpl();

        System.out.println("Test Nurse: " + userDao.getUserByName("Test Nurse"));
        System.out.println("Test Doctor: " + userDao.getUserByName("Test Doctor"));
        System.out.println("Test Absent: " + userDao.getUserByName("Test Absent"));

        session.close();
        sessionFactory.close();


    }
}
