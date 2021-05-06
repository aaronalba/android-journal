/**
 * Fragment class that shows the screen requesting the user to enter a pin before starting the app.
 * @author Aaron Alba
 */

package com.practice.journal.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.practice.journal.R;
import com.practice.journal.models.UserStash;

public class JournalLoginFragment extends Fragment {
    private EditText mPasswordField;
    private Button mLoginButton;
    private int mAttempts;

    public static final String EXTRA_LOGIN_STATUS = "login_status";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAttempts = 0;
    }


    /**
     * This method inflates the view that this fragment will use and get references to the widgets defined in that layout
     * @return the root view of the layout
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // get references to the widgets
        // the password field
        mPasswordField = view.findViewById(R.id.login_password_field);

        // the login button
        mLoginButton = view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = mPasswordField.getText().toString();

                // show the entered PIN temporarily
                Toast.makeText(getContext(), mPasswordField.getText() + " was entered!", Toast.LENGTH_SHORT).show();

                // hide the on-screen keyboard
                InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (manager != null) {
                    manager.hideSoftInputFromWindow(mPasswordField.getWindowToken(), 0);
                }

                // authenticate password
                boolean isAuthenticated = UserStash.get(getContext()).authenticateSingleUser(pin);
                if (isAuthenticated) {
                    // create the success result to the calling activity
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_LOGIN_STATUS, true);
                    getActivity().setResult(Activity.RESULT_OK, intent);

                    // stop the activity and return the result
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), getString(R.string.toast_pin_incorrect), Toast.LENGTH_SHORT).show();
                    mPasswordField.setText("");
                    mPasswordField.requestFocus();
                    mAttempts++;
                }
            }
        });

        return view;
    }
}
