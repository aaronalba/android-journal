/**
 * Utility Class for methods used in different Fragments and Activities in the application.
 * @author Aaron Alba
 */

package com.practice.journal;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class JournalUtil {
    // private constructor to avoid instantiation
    private JournalUtil() {}


    /**
     * Applies a format to a Date object and returns the string date.
     * @param date The Date object to be formatted.
     * @param isMilitaryTime Tells whether the format should be in military time or am-pm time.
     * @return The String containing the formatted date.
     */
    public static String formatDate(Date date, boolean isMilitaryTime) {
        // create the formatter
        SimpleDateFormat format;
        if (isMilitaryTime) {
            format = new SimpleDateFormat("E dd MMM yyyy \t HH:mm");
        }
        format = new SimpleDateFormat("E dd MMM yyyy \t hh:mm a");

        // format the date
        return format.format(date);
    }
}
