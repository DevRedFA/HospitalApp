package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.RoleDao;
import com.epam.hospital.model.Role;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
@Qualifier("roleDao")
public class RoleDaoImpl implements RoleDao {

    @Autowired
    SessionFactory sessionFactory;

    public Role getRoleById(int id) {
        Role role = null;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            role = session.get(Role.class, id);

        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return role;
    }

    public Role getRoleByName(String roleName) {
        Role role = null;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            role = (Role) session.createQuery("FROM Role WHERE name=:name")
                    .setParameter("name", roleName)
                    .uniqueResult();

        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return role;
    }

}
