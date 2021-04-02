/**
 * Fragment class for showing a list of entries using a RecyclerView.
 * @author Aaron Alba
 */

package com.practice.journal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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



    /*
        Adapter Class for feeding the RecyclerView with ViewHolders from the list of entries.
        This class creates the needed ViewHolder defined in JournalViewHolder and binds that
        view to the Data from the List of Journal Entries, then it is shown by the RecyclerView.
     */
    private class JournalAdapter extends RecyclerView.Adapter<JournalHolder> {
        private List<Entry> mList;


        public JournalAdapter(Context context) {
            this.mList = EntryStash.get(context).getEntries();
        }

        @NonNull
        @Override
        public JournalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JournalHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull JournalHolder holder, int position) {
            holder.bind(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
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


        public JournalHolder(LayoutInflater inflater, ViewGroup parent) {
            // inflate the layout. The root view can be found in the ViewHolder member field named itemView
            super(inflater.inflate(R.layout.list_item_entry, parent, false));

            // get references to the text view
            mTitleTextView = itemView.findViewById(R.id.entry_title);
            mDateTextView = itemView.findViewById(R.id.entry_date);
            mEditButton = itemView.findViewById(R.id.edit_button);
            mDeleteButton = itemView.findViewById(R.id.delete_button);
        }

        // This method is called by the adapter to fill the data in this ViewHolder
        public void bind(Entry entry) {
            mTitleTextView.setText(entry.getTitle());
            mDateTextView.setText(entry.getDate().toString());
        }
    }
}
