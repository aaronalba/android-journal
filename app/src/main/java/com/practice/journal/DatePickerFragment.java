/**
 * Class that shows a DatePicker dialog for letting the user pick date.
 * @author Aaron Alba
 */

package com.practice.journal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_DATE = "com.practice.journal.date";

    private static final String ARG_DATE = "date";

    /**
     * Convenience method for creating this fragment with the given Date object as an argument.
     * @param dateArg The Date that will be displayed in the date picker.
     * @return A DatePickerFragment.
     */
    public static DatePickerFragment newInstance(Date dateArg) {
        // create the bundle containing the Date argument
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, dateArg);

        // create and return this fragment and set the argument
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // retrieve the date object from the arguments
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        // create a Calendar object to extract the year, month and day from the Date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // create and return the DatePickerDialog (this is a subclass of an AlertDialog)
        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                this,
                year,
                month,
                day
        );

        return dialog;
    }


    /**
     * This is the listener method will be called when the user sets the date in the dialog.
     * @param view The DatePicker View.
     * @param year  The year chosen by the user.
     * @param month The month chosen by the user.
     * @param dayOfMonth The day chosen by the user.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // create a date object from the chosen year, month and day
        Date date = new GregorianCalendar(year,month,dayOfMonth).getTime();

        // send the result back to the caller of this fragment
        sendResult(date);
    }


    /**
     * Method for sending the Date that was chosen by the user back to the calling fragment using the Target Fragment.
     * @param date The Date chosen by the user.
     */
    private void sendResult(Date date) {
        // check if there is a target fragment
        if (getTargetFragment() == null) {
            return;
        }

        // create the intent that will hold the Date
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        // send the intent to the target fragment
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
