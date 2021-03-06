package com.epam.hospital.dao.api;

import com.epam.hospital.model.User;


public interface UserDao {
    User getUserById(int id);

    User getUserByName(String username);

    boolean saveOrUpdateUser(User user);
}

