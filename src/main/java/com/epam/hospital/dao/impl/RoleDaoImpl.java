package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.api.RoleDao;
import com.epam.hospital.model.Role;
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

        return sessionFactory.getCurrentSession().get(Role.class, id);

    }

    public Role getRoleByName(String roleName) {

        return (Role) sessionFactory.getCurrentSession().createQuery("FROM Role WHERE name=:name")
                .setParameter("name", roleName)
                .uniqueResult();

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