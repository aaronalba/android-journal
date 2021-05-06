/**
 * Fragment class that will handle the sign-up screen of the application
 * @author Aaron Alba
 */

package com.practice.journal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.practice.journal.R;
import com.practice.journal.models.User;
import com.practice.journal.models.UserStash;

public class JournalSignupFragment extends Fragment {
    private EditText mNameField;
    private EditText mPinField;
    private EditText mConfirmField;
    private Button mSignupButton;

    // key for the intent extra to be sent to the starting activity
    public static final String EXTRA_SIGNUP_STATUS = "signup_status";

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
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameField.getText().toString();
                String pin = mPinField.getText().toString();
                String confirm = mConfirmField.getText().toString();
                User user = new User(name, pin);

                // check if the name field is empty
                if (mNameField.getText().toString().length() == 0) {
                    Toast.makeText(getContext(), getString(R.string.toast_noname), Toast.LENGTH_SHORT).show();
                    mNameField.requestFocus();
                    return;
                }

                // check if the password is at least 4 digits
                if (mPinField.getText().toString().length() < 4) {
                    Toast.makeText(getContext(), getString(R.string.toast_pin_min_length), Toast.LENGTH_SHORT).show();
                    mPinField.requestFocus();
                    return;
                }

                // check user name validity
                if (UserStash.get(getContext()).hasUser(name)) {
                    Toast.makeText(getContext(), getString(R.string.toast_user_exist), Toast.LENGTH_SHORT).show();
                    return;
                }

                // if entered pin is equal to confirm pin
                if (pin.equals(confirm)) {

                    // create the user
                    UserStash.get(getContext()).createUser(new User("user", user.getPin()));    // temporarily hardcode the username to user
                    Toast.makeText(getContext(), getString(R.string.toast_user_created), Toast.LENGTH_SHORT).show();

                    // end the activity
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_SIGNUP_STATUS, true);
                    getActivity().finish();

                } else {
                    // pin not equal
                    Toast.makeText(getContext(), getString(R.string.toast_unmatched_pin), Toast.LENGTH_SHORT).show();
                    mPinField.setText("");
                    mConfirmField.setText("");
                    mPinField.requestFocus();
                }
            }
        });

        return v;
    }
}
