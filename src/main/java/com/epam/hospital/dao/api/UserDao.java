package com.epam.hospital.dao.api;

import com.epam.hospital.model.User;

public interface UserDao {
    User getUserByName(String username);
}
