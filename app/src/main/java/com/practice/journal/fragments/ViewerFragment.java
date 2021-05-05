/**
 * Fragment class that previews an Entry.
 * @author Aaron Alba
 */

package com.practice.journal.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.practice.journal.models.Entry;
import com.practice.journal.models.EntryStash;
import com.practice.journal.utils.JournalUtil;
import com.practice.journal.R;
import com.practice.journal.activities.JournalActivity;

import java.util.UUID;

public class ViewerFragment extends Fragment {
    private TextView titleTextField;
    private TextView dateTimeTextField;
    private TextView contentTextField;
    private Entry mEntry;

    private static final String ARGS_ENTRY_ID = "uuid";

    private static final String TAG_PROMPT_DELETE = "tag_prompt_delete";

    private static final int REQUEST_PROMPT_DELETE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retrieve the Entry id from the fragment argument
        UUID id = (UUID) getArguments().getSerializable(ARGS_ENTRY_ID);

        // get the Entry using the UUID
        mEntry = EntryStash.get(getContext()).getEntry(id);

        // enable the menu on the toolbar
        setHasOptionsMenu(true);
    }


    /**
     * This lifecycle method is called when the Fragment is on the foreground again
     */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    /**
     * Used in creating a ViewerFragment instance with an id argument telling which Entry in the
     * list of entries that the fragment will preview.
     * @param id The UUID of the Entry object.
     * @return ViewerFragment object.
     */
    public static ViewerFragment newInstance(UUID id) {
        // create the fragment arguments
        Bundle args = new Bundle();
        args.putSerializable(ARGS_ENTRY_ID, id);

        // return a new ViewerFragment with the specified arguments
        ViewerFragment fragment = new ViewerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * This lifecycle method inflates the layout resource that will be used by this fragment.
     * @param inflater  the layout inflater.
     * @param container the parent view that this fragment's UI should attach to.
     * @param savedInstanceState the Bundle containing data from a previous state.
     * @return the inflated view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View view = inflater.inflate(R.layout.fragment_viewer, container, false);

        // get the references to the Views in the layout and set its values
        titleTextField = view.findViewById(R.id.title_viewer);
        titleTextField.setText(mEntry.getTitle());

        dateTimeTextField = view.findViewById(R.id.datetime_viewer);
        dateTimeTextField.setText(JournalUtil.formatDateTime(mEntry.getDate(), false));

        contentTextField = view.findViewById(R.id.content_viewer);
        contentTextField.setText(mEntry.getContent());

        return view;
    }


    /**
     * Inflates the menu on the app's toolbar.
     * @param menu The menu.
     * @param inflater The layout for the menu.
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_viewer, menu);
    }


    /**
     * Defines the actions to be performed when an item in the options menu has been pressed.
     * @param item The menu item that was pressed.
     * @return boolean value, true if no more processing is needed.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                // create the intent to launch the new activity and start it
                Intent intent = JournalActivity.newIntent(getContext(), mEntry.getId());
                startActivity(intent);

                return true;

            case R.id.menu_delete:
                // show delete prompt to confirm delete operation
                PromptDeleteEntryFragment prompt = new PromptDeleteEntryFragment(
                        getString(R.string.prompt_delete_entry),
                        getString(R.string.delete),
                        mEntry
                );
                prompt.setTargetFragment(this, REQUEST_PROMPT_DELETE);
                prompt.show(getFragmentManager(), TAG_PROMPT_DELETE);

                return true;
        }

        // default case
        return super.onOptionsItemSelected(item);
    }


    /**
     * Defines actions to be performed when an Activity or Fragment that is called by this Fragment has returned.
     * @param requestCode the request code used to pick which activity has returned.
     * @param resultCode tells the return status.
     * @param data the data passed from the returned activity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            // do not process if there is an error in the result code
            return;
        }

        if (requestCode == REQUEST_PROMPT_DELETE) {
            // get the value from the prompt
            boolean value = (boolean) data.getSerializableExtra(PromptDeleteEntryFragment.EXTRA_PROMPT);

            // get the id of the entry to be deleted
            String uuidString = (String) data.getSerializableExtra(PromptDeleteEntryFragment.EXTRA_PROMPT_ENTRYID);
            UUID uuid = UUID.fromString(uuidString);

            // delete the entry depending on the retrieved boolean value
            if (value) {
                // delete the entry
                EntryStash.get(getContext()).deleteEntry(uuid);

                // go back the activity stack once, simulate pressing of the back button.
                getActivity().onBackPressed();
            }
        }
    }

    /*
            Updates the Entry object in this Viewer and the updated Entry's data will be used by the Views
         */
    private void updateUI() {
        // update the entry object
        mEntry = EntryStash.get(getContext()).getEntry(mEntry.getId());

        // update the Views
        titleTextField.setText(mEntry.getTitle());

        String dateTime = JournalUtil.formatDateTime(mEntry.getDate(), false);
        dateTimeTextField.setText(dateTime);

        contentTextField.setText(mEntry.getContent());
    }
}
