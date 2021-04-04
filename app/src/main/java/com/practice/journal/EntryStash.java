/**
 * Class that serves as an interface to the list of entries being maintained by the Journal App.
 * This class follows a singleton pattern to avoid problems in having multiple instances of the
 * list of journal entries.
 * @author Aaron Alba
 */

package com.practice.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.practice.journal.db.EntryDbOpenHelper;
import com.practice.journal.db.EntryDbSchema;
import com.practice.journal.db.EntryDbSchema.EntryTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntryStash {
    // the singleton instance of this class
    private static EntryStash sEntryStash;

    // the database that stores the entries
    private SQLiteDatabase mDatabase;


    // Private constructor to avoid instantiating objects from this class
    private EntryStash(Context context) {
        // create or open the database using the EntryDbOpenHelper class
        mDatabase = new EntryDbOpenHelper(context).getWritableDatabase();
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
        // TODO: update the implementation to use mDatabase
//        return mList;
        return null;
    }


    /**
     * Adds an entry to the list of journal entries.
     * @param entry The entry to be added to the list.
     */
    public void addEntry(Entry entry) {
        // TODO: update the implementation to use mDatabase
//        this.mList.add(entry);
    }


    /**
     * Updates an entry in the list of journal entries.
     * @param id The id of the Entry to be updated.
     * @param entry The updated entry.
     */
    public void updateEntry(UUID id, Entry entry) {
        // TODO: implement update of an entry in the list of entries
    }


    /**
     * Removes an entry in the list of journal entries.
     * @param id The id of the Entry to be deleted.
     */
    public void deleteEntry(UUID id) {
        // TODO: update the implementation to use mDatabase
//
//        // find the index of the Entry to be removed
//        for(int i=0; i<mList.size(); i++) {
//            // iterate over each Entry in the list
//            Entry e = mList.get(i);
//            if (e.getId().equals(id)) {
//                mList.remove(i);    // remove the entry with the matching id
//            }
//        }
    }


    /**
     * Returns an entry from the list of journal entries.
     * @param id The id of the entry to be retrieved.
     * @return The matching Entry object or null if the id does not match any Entry.
     */
    public Entry getEntry(UUID id) {
        // TODO: update the implementation to use mDatabase
        return null;

//        // iterate over each Entry in the list
//        for(int i=0; i<mList.size(); i++) {
//            Entry e = mList.get(i);
//
//            // compare the given id to the ids in each Entry
//            if (e.getId().equals(id)) {
//                return e;
//            }
//        }
//
//        return null;
    }
}
