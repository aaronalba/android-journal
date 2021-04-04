/**
 * Fragment class for showing a screen for creating and editing a journal entry
 * @author Aaron Alba
 */

package com.practice.journal;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class JournalFragment extends Fragment {
    private EditText mTitleField;
    private EditText mDateField;
    private EditText mTimeField;
    private EditText mContentField;
    private Entry mEntry;

    private static final String ARG_ENTRY_ID = "uuid";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retrieve the fragment argument to get the current entry to be shown
        Bundle args = getArguments();
        UUID id = (UUID) args.getSerializable(ARG_ENTRY_ID);

        // set the entry object
        mEntry = EntryStash.get(getContext()).getEntry(id);
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

        mDateField = view.findViewById(R.id.date_field);
        mDateField.setText(mEntry.getDate().toString());  // set the date field from the Entry

        mTimeField = view.findViewById(R.id.time_field);


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
        EntryStash.get(getContext()).updateEntry(mEntry.getId(), mEntry);    // update the entry
    }
}
