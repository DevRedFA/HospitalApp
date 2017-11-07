package com.epam.hospital.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


/**
 * Class create SessionFactory as singleton.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure();
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(ssrb.build());
        }
        return sessionFactory;
    }

}
