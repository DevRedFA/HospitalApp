package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.RoleDao;
import com.epam.hospital.model.Role;
import com.epam.hospital.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Qualifier("roleDao")
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

    @Override
    public boolean saveRole(Role role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateRole(Role role) {
        throw new UnsupportedOperationException();
    }
}
