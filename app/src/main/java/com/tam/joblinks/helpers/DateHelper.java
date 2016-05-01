package com.tam.joblinks.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by toan on 4/22/2016.
 */
public class DateHelper {
    public static final String MM_DD_YYYY_PATTERN = "MM/dd/yyyy";
    public static Date addDaysFromCurrentDate(int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, days);
        Date date = c.getTime();
        return date;
    }

    public static Date now() {
        return new Date();
    }

    public static String formatDate(Date date) {

        SimpleDateFormat dt1 = new SimpleDateFormat(MM_DD_YYYY_PATTERN);
        return dt1.format(date);
    }

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(MM_DD_YYYY_PATTERN);
        Date result = df.parse(dateString);
        return result;
    }
}


