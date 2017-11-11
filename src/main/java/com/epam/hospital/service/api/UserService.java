package com.epam.hospital.service.api;


import com.epam.hospital.model.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
