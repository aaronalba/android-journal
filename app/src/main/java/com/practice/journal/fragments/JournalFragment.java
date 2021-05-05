/**
 * Fragment class for showing a screen for creating and editing a journal entry
 * @author Aaron Alba
 */

package com.practice.journal.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.practice.journal.models.Entry;
import com.practice.journal.models.EntryStash;
import com.practice.journal.JournalUtil;
import com.practice.journal.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class JournalFragment extends Fragment {
    private EditText mTitleField;
    private Button mDateField;
    private Button mTimeField;
    private EditText mContentField;
    private Entry mEntry;

    private static final String ARG_ENTRY_ID = "uuid";

    private static final int REQUEST_DATE = 1;
    private static final int REQUEST_TIME = 2;

    private static final String TAG_DIALOG_DATE = "tag_date";
    private static final String TAG_DIALOG_TIME = "tag_time";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retrieve the fragment argument to get the current entry to be shown
        Bundle args = getArguments();
        UUID id = (UUID) args.getSerializable(ARG_ENTRY_ID);

        // set the entry object
        mEntry = EntryStash.get(getContext()).getEntry(id);

        // enable options menu in the toolbar of this activity
        setHasOptionsMenu(true);
    }



    /**
     * Used to create a JournalFragment with the given Id as an argument. The argument will be used
     * to specify which Entry will be shown so that the user can modify it using this Fragment.
     * @param entryId UUID of the Entry to be edited.
     * @return A JournalFragment showing an Entry.
     */
    public static JournalFragment newInstance(UUID entryId) {
        // create the argument
        Bundle args = new Bundle();
        args.putSerializable(ARG_ENTRY_ID, entryId);

        // create the JournalFragment
        JournalFragment fragment = new JournalFragment();
        fragment.setArguments(args);
        return fragment;
    }



    /**
     * This lifecycle method inflates the layout resource that will be used by this fragment.
     * @param inflater  the layout inflater.
     * @param container the parent view that this fragment's UI should attach to.
     * @param savedInstanceState the Bundle containing data from a previous state.
     * @return the inflated view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        // get references to the views in the layout

        // The TitleTextField
        mTitleField = view.findViewById(R.id.title_field);
        mTitleField.setText(mEntry.getTitle()); // set the title field from the Entry
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEntry.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });



        // The DateField
        mDateField = view.findViewById(R.id.date_field);
        updateDateText();
        mDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create the date picker fragment
                DatePickerFragment dialog = DatePickerFragment.newInstance(mEntry.getDate());

                // set JournalFragment as the target fragment of the DatePickerFragment so that the result can be sent back
                dialog.setTargetFragment(JournalFragment.this, REQUEST_DATE);

                // start the dialog fragment
                dialog.show(getFragmentManager(), TAG_DIALOG_DATE);
            }
        });


        // The TimeField
        mTimeField = view.findViewById(R.id.time_field);
        updateTimeText();
        mTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create the time picker fragment
                TimePickerFragment dialog = TimePickerFragment.newInstance(mEntry.getDate());

                // set JournalFragment as the target fragment of the DatePickerFragment so that the result can be sent back
                dialog.setTargetFragment(JournalFragment.this, REQUEST_TIME);

                // start the dialog fragment
                dialog.show(getFragmentManager(), TAG_DIALOG_TIME);
            }
        });


        // The ContentField
        mContentField = view.findViewById(R.id.content_field);
        mContentField.setText(mEntry.getContent()); // set the content field
        mContentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEntry.setContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }


    /**
     * This lifecycle method is called when the Fragment is removed from the viewable screen of the user.
     * When the user navigates back to the List showing the Entries on JournalListFragment, this method will
     * be called and it is saves the changes that the user made to the Entry object that is currently being
     * edited.
     */
    @Override
    public void onPause() {
        super.onPause();

        // update the entry
        syncDatabase();
    }


    /**
     * Inflates the layout for the toolbar options menu.
     * @param menu The menu object.
     * @param inflater  The layout inflater of the options menu.
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // inflate the menu layout
        inflater.inflate(R.menu.fragment_journal, menu);
    }


    /**
     * Defines the actions to be taken when a menu item in the toolbar has been pressed.
     * @param item The menu item that was clicked.
     * @return boolean value, true if further processing is not needed.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                getActivity().onBackPressed(); // simulate pressing of the back button
                syncDatabase(); // save the changes in the Entry to the database
                return true;
        }

        // default case
        return super.onOptionsItemSelected(item);
    }


    /**
     * Method to be called when a launched activity or fragment has returned.
     * @param requestCode   The request code used to launch the fragment or activity.
     * @param resultCode    The result code or the exit code of the launched activity or fragment.
     * @param data  An Intent containing returned data.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // check if the result code is OK
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            // the result got from the DatePickerFragment
            Date res = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);


            // get the year, month and day date from the retrieved date
            Calendar date_result = Calendar.getInstance();
            date_result.setTime(res);
            int year = date_result.get(Calendar.YEAR);
            int month = date_result.get(Calendar.MONTH);
            int day = date_result.get(Calendar.DAY_OF_MONTH);

            // get the time date from the current date
            Calendar date_current = Calendar.getInstance();
            date_current.setTime(mEntry.getDate());
            int hour = date_current.get(Calendar.HOUR_OF_DAY);
            int minute = date_current.get(Calendar.MINUTE);
            int sec = date_current.get(Calendar.SECOND);

            // create the final date
            GregorianCalendar date = new GregorianCalendar(year, month, day, hour, minute, sec);

            // set the new date to the date object of the Entry
            mEntry.setDate(date.getTime());

            // update the date text in the UI
            updateDateText();
            syncDatabase();

        } else if (requestCode == REQUEST_TIME) {

            // the Date result containing the chosen time from TimePickerFragment
            Date res = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);

            // get the year, month and day of the current date in the Entry
            Calendar date_current = Calendar.getInstance();
            date_current.setTime(mEntry.getDate());
            int year = date_current.get(Calendar.YEAR);
            int month = date_current.get(Calendar.MONTH);
            int day = date_current.get(Calendar.DAY_OF_MONTH);

            // get the hour and minute from the resulting date
            Calendar date_res = Calendar.getInstance();
            date_res.setTime(res);
            int hour = date_res.get(Calendar.HOUR_OF_DAY);
            int minute = date_res.get(Calendar.MINUTE);

            // create the final date
            GregorianCalendar date = new GregorianCalendar(year,month,day,hour,minute);

            // update the date object of the Entry
            mEntry.setDate(date.getTime());

            // update the time shown in the UI
            updateTimeText();
            syncDatabase();
        }
    }



    /*
        This method rewrites the text on the DateField View.
     */
    private void updateDateText() {
        // create the date string to be shown
        String dateString = JournalUtil.formatDate(mEntry.getDate());

        // set the date field
        mDateField.setText(dateString);
    }



    /*
        This method rewrites the text on the DateField View.
     */
    private void updateTimeText() {
        // create the time string to be shown in the TimeField Button
        String timeString = JournalUtil.formatTime(mEntry.getDate(), false);

        // set the time field
        mTimeField.setText(timeString);
    }


    /*
        This method updates the data modified in the Entry to the Database handled by EntryStash
     */
    private void syncDatabase() {
        // update the entry in the database
        EntryStash.get(getContext()).updateEntry(mEntry.getId(), mEntry);
    }
}
