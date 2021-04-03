/**
 * Activity class for previewing an Entry.
 * @author Aaron A. Alba
 */

package com.practice.journal;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class ViewerActivity extends SingleFragmentActivity {

    private static final String EXTRA_ENTRY_ID = "com.practice.journal.entryid";

    /**
     * Defines the fragment that will be hosted by this Activity.
     * @return The Fragment to be hosted.
     */
    @Override
    protected Fragment createFragment() {
        // retrieve the id from the intent extra and use that id for creating the ViewerFragment
        UUID id = (UUID) getIntent().getSerializableExtra(EXTRA_ENTRY_ID);
        return ViewerFragment.newInstance(id);
    }


    /**
     * Creates a new intent for launching this activity.
     * @param packageContext The activity that will start this ViewerActivity.
     * @param id The intent extra which contains the UUID of the Entry that will be previewed.
     * @return Intent object for starting this activity.
     */
    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = new Intent(packageContext, ViewerActivity.class);
        intent.putExtra(EXTRA_ENTRY_ID, id);
        return intent;
    }
}
