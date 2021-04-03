/**
 * Activity class for showing a list of entries in the journal.
 * @author Aaron Alba
 */

package com.practice.journal;

import androidx.fragment.app.Fragment;

public class JournalListActivity extends SingleFragmentActivity {

    /**
     * Defines the fragment that will be hosted by this activity.
     * @return The Fragment to be hosted.
     */
    @Override
    protected Fragment createFragment() {
        return new JournalListFragment();
    }
}
