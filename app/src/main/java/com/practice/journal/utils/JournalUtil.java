/**
 * Utility Class for methods used in different Fragments and Activities in the application.
 * @author Aaron Alba
 */

package com.practice.journal.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class JournalUtil {
    // private constructor to avoid instantiation
    private JournalUtil() {}


    /**
     * Applies a format to a Date object and returns the string date time.
     * @param date The Date object to be formatted.
     * @param isMilitaryTime Tells whether the format should be in military time or am-pm time.
     * @return The String containing the formatted date time.
     */
    public static String formatDateTime(Date date, boolean isMilitaryTime) {
        // create the formatter
        SimpleDateFormat format;
        if (isMilitaryTime) {
            format = new SimpleDateFormat("E dd MMM yyyy \t HH:mm");
        }
        format = new SimpleDateFormat("E dd MMM yyyy \t hh:mm a");

        // format the date
        return format.format(date);
    }


    /**
     * Applies a format to a Date object and returns the string date.
     * @param date The Date object to be formatted.
     * @return The String containing the formatted date.
     */
    public static String formatDate(Date date) {
        // create the formatter
        SimpleDateFormat format = new SimpleDateFormat("E dd MMM yyyy");

        // format the date
        return format.format(date);
    }


    /**
     * Applies a format to a Date object and returns the string time.
     * @param date The Date object to be formatted.
     * @return The string containing the time from the Date.
     */
    public static String formatTime(Date date, boolean isMilitaryTime) {
        // create the formatter
        SimpleDateFormat format;
        if (isMilitaryTime) {
            format = new SimpleDateFormat("HH:mm");
        }
        format = new SimpleDateFormat("hh:mm a");

        // format the date
        return format.format(date);
    }
}
