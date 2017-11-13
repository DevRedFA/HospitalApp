package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.UserDao;
import com.epam.hospital.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("userDao")
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    public User getUserById(int id) {
        User user = null;
        Session session = null;
        try {
            user = session.get(User.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return user;

    }

    public User getUserByName(String username) {
        User user = null;
        Session session = null;
        try {
            user = (User) sessionFactory.getCurrentSession().createQuery("FROM User WHERE username=:username")
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return user;

    }

    public boolean saveOrUpdateUser(User user) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(user);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return true;
    }

}
