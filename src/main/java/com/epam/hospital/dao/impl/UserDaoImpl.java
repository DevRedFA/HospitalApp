package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.UserDao;
import com.epam.hospital.model.User;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public User getUserByName(String username) {
        Session session = sessionFactory.openSession();

        User user = (User) session
                .createQuery("FROM User WHERE username=:username")
                .setParameter("username", username)
                .uniqueResult();

        session.close();
        return user;
    }

    @Transactional
    public User getUserByName5(String username) {
        User user = null;
        Session session = sessionFactory.openSession();

        try {
            user = (User) session
                    .createQuery("FROM User WHERE username=:username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            //log exception if needed
        }

        session.close();
        return user;
    }

    @Transactional
    public User getUserById(int id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Transactional
    public boolean saveUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
        return false;             // change if needed or make method type void
    }

    @Transactional
    public boolean updateUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
        session.close();
        return false;             // change if needed or make method type void
    }

    @Transactional
    public void deleteUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Transactional
    public List<User> getAll() {
        Session session = sessionFactory.openSession();
        List<User> result = session.createQuery("FROM User").list();
        session.close();
        return result;
    }

}
