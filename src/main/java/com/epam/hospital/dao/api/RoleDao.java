package com.epam.hospital.dao.api;

import com.epam.hospital.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer>  {

}
