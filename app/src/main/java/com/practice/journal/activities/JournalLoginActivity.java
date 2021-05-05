package com.practice.journal.activities;

import androidx.fragment.app.Fragment;

import com.practice.journal.fragments.JournalLoginFragment;

public class JournalLoginActivity extends SingleFragmentActivity {

    /**
     * Defines the fragment that will be hosted by this activity.
     * @return The Fragment to be hosted by this activity.
     */
    @Override
    protected Fragment createFragment() {
        return new JournalLoginFragment();
    }
}
