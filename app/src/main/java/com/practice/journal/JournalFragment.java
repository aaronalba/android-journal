/**
 * Fragment class for showing a screen for creating and editing a journal entry
 * @author Aaron Alba
 */

package com.practice.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class JournalFragment extends Fragment {
    private EditText mTitleField;
    private EditText mDateField;
    private ImageButton mDateButton;
    private EditText mContentField;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mDateField = view.findViewById(R.id.date_field);

        mDateButton = view.findViewById(R.id.date_button);

        mContentField = view.findViewById(R.id.content_field);

        return view;
    }
}
