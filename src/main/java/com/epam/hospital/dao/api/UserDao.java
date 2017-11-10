package com.epam.hospital.dao.api;

import com.epam.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {


    User findByUsername(String username);




}
