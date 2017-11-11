package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.UserDao;
import com.epam.hospital.model.User;
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

        return sessionFactory.getCurrentSession().get(User.class, id);

    }

     public User getUserByName(String username) {

        return (User) sessionFactory.getCurrentSession().createQuery("FROM User WHERE username=:username")
                .setParameter("username", username)
                .uniqueResult();

    }

    public boolean saveOrUpdateUser(User user) {

        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return true;

    }

}
