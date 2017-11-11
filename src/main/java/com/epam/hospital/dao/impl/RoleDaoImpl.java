package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.RoleDao;
import com.epam.hospital.model.Role;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class RoleDaoImpl implements RoleDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public Role getRoleById(int id) {
        Role role = null;
        try (Session session = sessionFactory.openSession()) {
            role = session.get(Role.class, id);
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }

        return role;
    }

    @Transactional
    public Role getRoleByName(String roleName) {
        Role role = null;
        try (Session session = sessionFactory.openSession()) {
            role = (Role) session.createQuery("FROM Role WHERE name=:name")
                    .setParameter("name", roleName)
                    .uniqueResult();
        } catch (HibernateException hibEx) {
            throw new RuntimeException(hibEx);
        }
        return role;
    }
}
