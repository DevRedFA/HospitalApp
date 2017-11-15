package com.epam.hospital.util;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Set;

public class Utils {
    public static String formatTime(Timestamp timestamp) {
        if (timestamp != null) {
            return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);
        }
        return "";
    }

    public static String getRole(User user) {
        Set<Role> roles = user.getRoles();
        if (roles.size() > 1) {
            throw new RuntimeException("User has more then one role");
        }
        String userRole = null;
        for (Role role : roles) {
            userRole = role.getName();
        }
        assert userRole != null;
        return userRole;
    }
}
