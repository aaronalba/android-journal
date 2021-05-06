package com.practice.journal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.practice.journal.fragments.JournalLoginFragment;

public class JournalLoginActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Defines the fragment that will be hosted by this activity.
     * @return The Fragment to be hosted by this activity.
     */
    @Override
    protected Fragment createFragment() {
        return new JournalLoginFragment();
    }
}
