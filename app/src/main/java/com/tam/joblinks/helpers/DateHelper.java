package com.tam.joblinks.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by toan on 4/22/2016.
 */
public class DateHelper {

    public static Date addDaysFromCurrentDate(int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 5);
        Date date = c.getTime();
        return date;
    }

    public static Date now() {
        return new Date();
    }

    public static String formatDate(Date date) {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat dt1 = new SimpleDateFormat(pattern);
        return dt1.format(date);
    }
}


