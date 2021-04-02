/**
 * Fragment class for showing a list of entries using a RecyclerView.
 * @author Aaron Alba
 */

package com.practice.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class JournalListFragment extends Fragment {
    private RecyclerView mRecyclerView;

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

        return view;
    }
}
