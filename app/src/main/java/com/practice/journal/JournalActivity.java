/**
 * Activity class that shows the screen for creating or editing a Journal Entry
 * @author Aaron Alba
 */

package com.practice.journal;

import androidx.fragment.app.Fragment;

public class JournalActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new JournalFragment();
    }
}