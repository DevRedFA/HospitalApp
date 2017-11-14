package com.epam.hospital.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utils {
    public static String formatTime(Timestamp timestamp) {
        if (timestamp != null) {
            return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);
        }
        return "";
    }
}
