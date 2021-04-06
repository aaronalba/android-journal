/**
 * Fragment class for showing a list of entries using a RecyclerView.
 * @author Aaron Alba
 */

package com.practice.journal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
import java.util.UUID;

public class JournalListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private JournalAdapter mAdapter;
    private LinearLayout mFirstEntryView;
    private Button mNewEntryButton;

    private static final int REQUEST_PROMPT_DELETE = 1;

    private static final String TAG_PROMPT_DELETE = "tag_prompt_delete";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    // tell the fragment that the options menu will be used
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
        View view = inflater.inflate(R.layout.fragment_journal_list, container, false);

        // get reference to the linear layout containing the first entry view and button
        mFirstEntryView = view.findViewById(R.id.list_empty);
        mNewEntryButton = view.findViewById(R.id.new_entry);

        // TODO: set listener to new entry button



        // get reference to the recycler view
        mRecyclerView = view.findViewById(R.id.entry_recycler_view);

        // setup the recycler view by creating the adapter and setting the layout manager
        mAdapter = new JournalAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);


        // update the UI of the Fragment
        updateUI();

        return view;
    }


    /**
     * Lifecycle method that is called when the Activity is brought back to the visible screen of the user.
     */
    @Override
    public void onResume() {
        super.onResume();
        updateUI(); // update the items in the recycler view since the list might have changed
    }


    /**
     * This method inflates the options menu.
     * @param menu The options menu.
     * @param inflater The layout inflater of the menu.
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_journal_list, menu);   // inflate the menu layout
    }


    /**
     * This method sets the actions to be performed when an option menu has been clicked
     * @param item The fired menu item.
     * @return a boolean value telling the OS about the status of the action.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_entry:
                // this menu option creates a new entry in the journal and launches
                // a JournalActivity so that the new entry can be edited.

                // create a new entry
                Entry entry = new Entry();

                // add the newly created entry to the list of entries
                EntryStash.get(getContext()).addEntry(entry);

                // launch the JournalActivity
                Intent intent = JournalActivity.newIntent(getContext(), entry.getId());
                startActivity(intent);

                return true;
        }


        return super.onOptionsItemSelected(item);   // default case when the menu item id does not match any known menu item
    }


    /*
        This method updates the UI of the fragment.
        functions:
            - Shows the new entry button if the list is empty.
            - Tells the adapter to update the UI of the recyclerview using the latest list from EntryStash
     */
    private void updateUI() {
        // get the current list of Entries
        List<Entry> list = EntryStash.get(getContext()).getEntries();

        // check if the new entry button should be shown
        if (list.size() < 1) {
            showFirstEntryView(true);
        } else {
            showFirstEntryView(false);
        }

        // update the list held by the Adapter
        mAdapter.setList(list);

        // tell the adapter to update the items in the recycler view using the new list that was set
        mAdapter.notifyDataSetChanged();
    }


    /*
        Adapter Class for feeding the RecyclerView with ViewHolders from the list of entries.
        This class creates the needed ViewHolder defined in JournalViewHolder and binds that
        view to the Data from the List of Journal Entries, then it is shown by the RecyclerView.
     */
    private class JournalAdapter extends RecyclerView.Adapter<JournalHolder> {
        // the list containing the Entry data set for the recycler view to use
        private List<Entry> mList;

        // creates an adapter with the given list of entries
        public JournalAdapter(Context context) {
            this.mList = EntryStash.get(context).getEntries();
        }


        // creates the view holders that the recycler view will use
        @NonNull
        @Override
        public JournalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JournalHolder(getLayoutInflater(), parent);
        }


        // binds the data from mList to the corresponding view holder upon the request of the recycler view
        @Override
        public void onBindViewHolder(@NonNull JournalHolder holder, int position) {
            holder.bind(mList.get(position));
        }

        // the number of items in the data set held by this adapter
        @Override
        public int getItemCount() {
            return mList.size();
        }


        // This method updates the data set of this adapter, used for updating the UI of the recycler view
        // @params list The list containing the Entries
        public void setList(List<Entry> list) {
            mList = list;
        }
    }

    /*
        ViewHolder Class that inflates the Views that will be shown by the RecyclerView.
        This class essentially defines the views inflated in the list_item_entry.xml
     */
    private class JournalHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageButton mEditButton;
        private ImageButton mDeleteButton;
        private Entry mEntry;

        public JournalHolder(LayoutInflater inflater, ViewGroup parent) {
            // inflate the layout. The root view can be found in the ViewHolder member field named itemView
            super(inflater.inflate(R.layout.list_item_entry, parent, false));

            // get references to the text view
            mTitleTextView = itemView.findViewById(R.id.entry_title);
            mDateTextView = itemView.findViewById(R.id.entry_date);
            mEditButton = itemView.findViewById(R.id.edit_button);
            mDeleteButton = itemView.findViewById(R.id.delete_button);

            // set the click listeners to the buttons
            mEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClicked(v);
                }
            });

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(v);
                }
            });

            // set the click listener for when this ViewHolder is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewHolderClick(v);
                }
            });
        }

        // This method is called by the adapter to fill the data in this ViewHolder
        public void bind(Entry entry) {
            mEntry = entry;
            mTitleTextView.setText(mEntry.getTitle());

            // format the date string before showing to the list
            String dateTimeString = JournalUtil.formatDateTime(mEntry.getDate(), false);
            mDateTextView.setText(dateTimeString);
        }

        // Method to be called when the edit button is clicked
        public void onEditClicked(View v) {
            // Clicking the edit button will launch the JournalActivity to allow edits to the entry
            Intent intent = JournalActivity.newIntent(getContext(), mEntry.getId());
            startActivity(intent);
        }

        // Method to be called when the delete button is clicked
        public void onDeleteClicked(View v) {
            // show a prompt to confirm delete operation
            PromptDeleteEntryFragment dialog = new PromptDeleteEntryFragment(
                    getString(R.string.prompt_delete_entry),
                    getString(R.string.prompt_confirm),
                    mEntry
            );
            dialog.setTargetFragment(JournalListFragment.this, REQUEST_PROMPT_DELETE);
            dialog.show(getFragmentManager(), TAG_PROMPT_DELETE);
        }

        // Method to be called when this view holder is clicked
        public void onViewHolderClick(View v) {
            // This method will launch the ViewerActivity for previewing an entry
            Intent intent = ViewerActivity.newIntent(getContext(), mEntry.getId());
            startActivity(intent);
        }
    }


    /**
     * Method to be called when a launched activity or fragment has returned.
     * @param requestCode   The request code used to launch the fragment or activity.
     * @param resultCode    The result code or the exit code of the launched activity or fragment.
     * @param data  An Intent containing returned data.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // check if the result is OK
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PROMPT_DELETE) {
            // retrieve the boolean result from the dialog
            boolean val = (boolean) data.getSerializableExtra(PromptDialogFragment.EXTRA_PROMPT);

            // retrieve the Entry id from the dialog
            String uuidString = (String) data.getSerializableExtra(PromptDeleteEntryFragment.EXTRA_PROMPT_ENTRYID);
            UUID uuid = UUID.fromString(uuidString);

            // if val is true delete the entry
            if (val) {
                EntryStash.get(getContext()).deleteEntry(uuid);
                updateUI();
            }
        }
    }


    /*
        Shows or Hides the TextView and Button for helping the user in adding their first Journal Entry.
        They should only be seen when the list of entries is empty.
     */
    private void showFirstEntryView(boolean visible) {
        // set the visibility to GONE
        if (visible) {
            mFirstEntryView.setVisibility(View.VISIBLE);
        } else {
            mFirstEntryView.setVisibility(View.GONE);
        }

    }
}
