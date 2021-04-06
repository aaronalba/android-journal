/**
 * Fragment Class that hosts a Time Picker to let the user choose the Time in the Journal Entry.
 * @author Aaron Alba
 */

package com.practice.journal;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    private static final String ARG_TIME = "time";

    public static final String EXTRA_TIME = "com.practice.journal.time";

    /**
     * Convenience method for creating this fragment with the given Date object as an argument.
     * @param date The Date object containing the time that will be shown in the picker.
     * @return A TimePickerFragment.
     */
    public static TimePickerFragment newInstance(Date date) {
        // create the bundle containing the date argument
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);

        // create and return a TimePickerFragment with the arguement set
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // retrieve the date object from the fragment argument
        Date date = (Date) getArguments().getSerializable(ARG_TIME);

        // Get the time date from the Date object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create an instance of a TimePicker Dialog (it is a subclass of AlertDialog)
        TimePickerDialog dialog = new TimePickerDialog(
                getActivity(),
                this,
                hour,
                minute,
                false);

        return dialog;
    }


    /**
     * Method called when the user has chosen a Time.
     * @param view The TimePicker view.
     * @param hourOfDay The Hour chosen by the user.
     * @param minute The Minute chosen by the user.
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        GregorianCalendar date = new GregorianCalendar(1,1,1,hourOfDay, minute,0);
        sendResult(date.getTime());
    }


    /**
     * Method for sending the Date that was chosen by the user back to the calling fragment using the Target Fragment.
     * @param date The Date object containing the time chosen by the user.
     */
    private void sendResult(Date date) {
        // check if there is a target fragment
        if (getTargetFragment() == null) {
            return;
        }

        // create the intent that will contain the date to be sent back to the caller
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);

        // send the result back using the Target Fragment
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
