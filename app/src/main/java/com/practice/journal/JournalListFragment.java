/**
 * Fragment class for showing a list of entries using a RecyclerView.
 * @author Aaron Alba
 */

package com.practice.journal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JournalListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private JournalAdapter mAdapter;

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

        // get reference to the recycler view
        mRecyclerView = view.findViewById(R.id.entry_recycler_view);

        // setup the recycler view by creating the adapter and setting the layout manager
        mAdapter = new JournalAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

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
                EntryStash.get(getContext()).getEntries().add(entry);

                // launch the JournalActivity
                Intent intent = JournalActivity.newIntent(getContext(), entry.getId());
                startActivity(intent);

                return true;
        }


        return super.onOptionsItemSelected(item);   // default case when the menu item id does not match any known menu item
    }


    // this updates the items shown in the RecyclerView
    private void updateUI() {
        // get the current list of Entries
        List<Entry> list = EntryStash.get(getContext()).getEntries();

        // update the list in the adapter
        mAdapter.setList(list);
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
            mDateTextView.setText(mEntry.getDate().toString());
        }

        // Method to be called when the edit button is clicked
        public void onEditClicked(View v) {
            // Clicking the edit button will launch the JournalActivity to allow edits to the entry
            Intent intent = JournalActivity.newIntent(getContext(), mEntry.getId());
            startActivity(intent);
        }

        // Method to be called when the delete button is clicked
        public void onDeleteClicked(View v) {
            // This deletes an Entry that was clicked
            EntryStash.get(getContext()).deleteEntry(mEntry.getId());

            // Shows a message telling that the entry was deleted
            Toast.makeText(getContext(), getString(R.string.toast_entry_delete), Toast.LENGTH_SHORT).show();

            // update the recycler views list of items
            updateUI();
        }

        // Method to be called when this view holder is clicked
        public void onViewHolderClick(View v) {
            // This method will launch the ViewerActivity for previewing an entry
            Intent intent = ViewerActivity.newIntent(mEntry.getId());
            startActivity(intent);
        }
    }
}
