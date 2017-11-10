package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.UserDao;
import com.epam.hospital.model.User;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public User getUserById(int id) {
        User user = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            user = (User) session.get(User.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return user;
    }

    @Transactional
    public User getUserByName(String username) {
        User user = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            user = (User) session.createQuery("FROM User WHERE username=:username")
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    @Transactional
    public boolean saveOrUpdateUser(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            if (transaction != null && !transaction.wasCommitted()) {
                transaction.rollback();
            }
        }
        return true;
    }

}
