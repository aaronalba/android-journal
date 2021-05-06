/**
 * Fragment class that will handle the sign-up screen of the application
 * @author Aaron Alba
 */

package com.practice.journal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.practice.journal.R;

public class JournalSignupFragment extends Fragment {
    private EditText mNameField;
    private EditText mPinField;
    private EditText mConfirmField;
    private Button mSignupButton;

    /**
     * Inflates the layout that will be used by this fragment. Also retrieves references to the widgets defined in that layout.
     * @param inflater the layout inflater.
     * @param container the parent view.
     * @param savedInstanceState saved data.
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup, container,false);

        // get the name field
        mNameField = v.findViewById(R.id.signup_name_field);

        // get the pin field
        mPinField = v.findViewById(R.id.signup_pin_field);

        // get the confirm pin field
        mConfirmField = v.findViewById(R.id.signup_pin_field_confirm);


        // get the signup button
        mSignupButton = v.findViewById(R.id.signup_button);

        return v;
    }



}
