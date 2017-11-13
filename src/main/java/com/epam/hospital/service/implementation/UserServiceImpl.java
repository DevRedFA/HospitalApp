package com.epam.hospital.service.implementation;

import com.epam.hospital.dao.api.RoleDao;
import com.epam.hospital.dao.api.UserDao;
import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getRoleById(3));
        user.setRoles(roles);
        userDao.saveOrUpdateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.getUserByName(username);
    }
}
