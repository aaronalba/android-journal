/**
 * Activity class for showing the application's sign-up screen.
 * @author Aaron Alba
 */

package com.practice.journal.activities;

import androidx.fragment.app.Fragment;

import com.practice.journal.fragments.JournalSignupFragment;

public class JournalSignupActivity extends SingleFragmentActivity {
    /**
     * Defines the fragment that will be hosted by this activity
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return new JournalSignupFragment();
    }
}
