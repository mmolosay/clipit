package com.ordolabs.clipit.util;

import android.text.TextUtils;

import com.ordolabs.clipit.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ordogod on 10.10.2019.
 **/

public class PrettyDate {

    private static final String DATE_PATTERN_FULL = "dd MM yy HH:mm EEE MMM"; // 24 10 19 22:47 thu oct.
    private static final String DATE_PATTERN_PART = "dd MM yy"; // 24 10 19
    private static final SimpleDateFormat DATE_FORMAT_PART =
            new SimpleDateFormat(DATE_PATTERN_PART, App.getContext().getResources().getConfiguration().locale);

    /**
     * Makes representation of current date in {@link #DATE_PATTERN_FULL} format.
     *
     * @return date of {@link String} type.
     **/
    public static String now() {
        String[] date = new SimpleDateFormat(
                DATE_PATTERN_FULL,
                App.getContext().getResources().getConfiguration().locale
        ).format(new Date()).split(" ");

        date[4] = capFirtsLetter(date[4]);
        date[5] = capFirtsLetter(date[5]).replaceAll("\\.", "");

        return TextUtils.join(" ", date);
    }

    /**
     * Makes nice text representation of relation between current date and the given one.
     *
     * @param date date string in {@link #DATE_PATTERN_FULL} format.
     */
    public static String from(final String date) {
        // 24 10 19 22:47 Thu Oct
        String[] dateFull = date.split(" ");
        String[] nowFull  = now().split(" ");

        int d = toInt(dateFull[0]);
        int m = toInt(dateFull[1]);
        int y = toInt(dateFull[2]);

        int _d = toInt(nowFull[0]);
        int _y = toInt(nowFull[2]);

        if (sameYear(_y, y))
            if (thisWeek(dateFull, nowFull))
                if (sameDay(_d, d)) return dateFull[3]; // 22:47
                else return dateFull[4];                // Thu
            else return dateFull[5] + " " + d;          // Oct 24
        else return d + "." + m + "." + y;              // 24.10.19
    }

    // 'same' means number equality
    private static boolean sameYear(int y1, int y2) {
        return (y1 == y2);
    }

    // 'this' means in range of one week's length
    private static boolean thisWeek(String[] date1, String[] date2) {
        // 24 10 19
        String pure1 = TextUtils.join(" ", new String[] {date1[0], date1[1], date1[2]});
        String pure2  = TextUtils.join(" ", new String[] {date2[0], date2[1], date2[2]});
        return (daysBetween(pure1, pure2) <= 6);
    }

    private static boolean sameDay(int d1, int d2) {
        return (d1 == d2);
    }

    private static String capFirtsLetter(final String s) {
        if (s.length() == 0) return s;
        if (s.length() == 1) return s.toUpperCase();
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private static int toInt(final String s) {
        return Integer.parseInt(s);
    }

    /**
     * Counts days between specified dates.
     *
     * @param date1 date String in {@link #DATE_PATTERN_PART} format.
     * @param date2 date String in {@link #DATE_PATTERN_PART} format.
     *
     * @return days count.
     */
    private static int daysBetween(String date1, String date2) {
        try {
            Date d1 = DATE_FORMAT_PART.parse(date1);
            Date d2 = DATE_FORMAT_PART.parse(date2);
            long diff = Math.abs(d2.getTime() - d1.getTime());
            return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }
        catch (ParseException e) { e.printStackTrace(); }
        return Integer.MIN_VALUE; // weird value to notice if smth went wrong
    }
}
