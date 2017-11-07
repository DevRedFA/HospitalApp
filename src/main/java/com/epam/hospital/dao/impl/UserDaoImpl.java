package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.UserDao;
import com.epam.hospital.model.User;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public User getUserByName(String username) {

        User user = (User) sessionFactory.openSession()
                .createQuery("FROM User WHERE username=:username")
                .setParameter("username", username)
                .uniqueResult();
        return user;
    }
}
