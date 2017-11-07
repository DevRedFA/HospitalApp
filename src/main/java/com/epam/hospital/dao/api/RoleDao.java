package com.epam.hospital.dao.api;

import com.epam.hospital.model.Role;

public interface RoleDao {
    Role getRoleById(int id);

    Role getRoleByName(String username);

    boolean saveRole(Role role);

    boolean updateRole(Role role);
}
