/**
 * Class that serves as an interface to the list of entries being maintained by the Journal App.
 * This class follows a singleton pattern to avoid problems in having multiple instances of the
 * list of journal entries.
 * @author Aaron Alba
 */

package com.practice.journal;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class EntryStash {
    // the singleton instance of this class
    private static EntryStash sEntryStash;

    // the list containing entries
    private List<Entry> mList;


    // Private constructor to avoid instantiating objects from this class
    private EntryStash(Context context) {
        mList = new ArrayList<>();
    }


    /**
     * This method returns the only instance of this class.
     * @param context The application context to be used in opening an SQLite Database as a backend for this application. (future implementation)
     * @return The single instance of this EntryStash.
     */
    public static EntryStash get(Context context) {
        if (sEntryStash == null) {
            sEntryStash = new EntryStash(context);
        }
        return sEntryStash;
    }


    /**
     * Returns the list of journal entries.
     * @return List
     */
    public List<Entry> getEntries() {
        return mList;
    }


    /**
     * Adds an entry to the list of journal entries.
     * @param entry The entry to be added to the list.
     */
    public void addEntry(Entry entry) {
        this.mList.add(entry);
    }


    public void updateEntry() {
        // TODO: implement update of an entry in the list of entries
    }


    public void deleteEntry() {
        // TODO: implement deletion of an entry in the list of entries
    }
}
