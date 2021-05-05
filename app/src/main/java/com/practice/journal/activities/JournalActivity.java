/**
 * Activity class that shows the screen for creating or editing a Journal Entry
 * @author Aaron Alba
 */

package com.practice.journal.activities;

import android.content.Context;
import android.content.Intent;


import androidx.fragment.app.Fragment;

import com.practice.journal.fragments.JournalFragment;

import java.util.UUID;

public class JournalActivity extends SingleFragmentActivity {

    private static final String EXTRA_ENTRY_ID = "com.practice.journal.entryid";


    /**
     * This method creates the fragment that will be hosted by this activity. This method is used
     * in the template class named SingleFragmentActivity.
     * @return The fragment that this activity will host.
     */
    @Override
    protected Fragment createFragment() {
        // create the fragment by using its newInstance method to pass in the need UUID argument
        return JournalFragment.newInstance((UUID) getIntent().getSerializableExtra(EXTRA_ENTRY_ID));
    }


    /**
     * This creates an intent that can be used to launch this activity.
     * @param context The activity that will launch this intent.
     * @param id The id argument needed to create the JournalFragment.
     * @return  The intent that can be used to launch JournalActivity.
     */
    public static Intent newIntent(Context context, UUID id) {
        Intent intent = new Intent(context, JournalActivity.class);
        intent.putExtra(EXTRA_ENTRY_ID, id);
        return intent;
    }
}