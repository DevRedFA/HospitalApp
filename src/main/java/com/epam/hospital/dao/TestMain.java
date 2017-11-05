package com.epam.hospital.dao;

import com.epam.hospital.model.Diagnose;
import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TestMain {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtill.getSessionFactory();
        Session session = sessionFactory.openSession();

        User user = (User) session.get(User.class, 1);
        System.out.println(user);
        session.close();
        sessionFactory.close();

//       
    }
}
