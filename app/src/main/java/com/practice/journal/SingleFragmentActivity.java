/**
 * Template class that can be used for Activities that need to host a single fragment.
 * Author: Aaron Alba
 */

package com.practice.journal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inflate the layout resource for this activity
        setContentView(R.layout.activity_fragment); // layout that can host a single fragment

        // try to find the fragment in the fragment manager
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        // create the fragment if it is not yet in the fragment manager and add it to the manager
        if (fragment == null) {
            fragment = createFragment();    // create the fragment defined by the subclass
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();  // finish the addition of the fragment
        }

    }


    /**
     * The subclass should define in this method the fragment that will be hosted in this single fragment activity.
     * @return The fragment to be added to the activity.
     */
    protected abstract Fragment createFragment();
}
