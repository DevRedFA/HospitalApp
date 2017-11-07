package com.epam.hospital;

import com.epam.hospital.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TestMain {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();




    }
}
