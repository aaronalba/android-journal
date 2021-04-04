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
        return new ArrayList<>();
    }


    /**
     * Adds an entry to the list of journal entries.
     * @param entry The entry to be added to the database.
     */
    public void addEntry(Entry entry) {
        // create the content values from the given entry
        ContentValues data = getContentValues(entry);

        // insert data to the database
        mDatabase.insert(EntryTable.NAME, null, data);
    }


    /**
     * Updates an entry in the list of journal entries.
     * @param id The id of the Entry to be updated.
     * @param entry The updated entry.
     */
    public void updateEntry(UUID id, Entry entry) {
        // get the uuid of the entry
        String uuidString = entry.getId().toString();

        // create the content values which will be passed to the update statement
        ContentValues data = getContentValues(entry);

        // update the data on the database
        mDatabase.update(
                EntryTable.NAME,
                data,
                EntryTable.COLS.UUID + "= ? ",
                new String[] { uuidString });
    }


    /**
     * Removes an entry in the list of journal entries.
     * @param id The id of the Entry to be deleted.
     */
    public void deleteEntry(UUID id) {
        // TODO: update the implementation to use mDatabase
        // get the id of the entry to be deleted
        String uuidString = id.toString();

        // run the delete operation on the database
        mDatabase.delete(EntryTable.NAME,
                EntryTable.COLS.UUID + " = ?",
                new String[] { uuidString });
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


    /*
        Returns a content values object containing the data from the given Entry object. This is
        used in database operations since the methods there requires a content value to pass in the
        data that needs to be written to the tables.

        @param entry The Entry object that contains journal data.
        @return The created Content Value from the given Entry.
     */
    private static ContentValues getContentValues(Entry entry) {
        // create the content values
        ContentValues values = new ContentValues();

        // insert data to the content values
        values.put(EntryTable.COLS.TITLE, entry.getTitle());
        values.put(EntryTable.COLS.UUID, entry.getId().toString());
        values.put(EntryTable.COLS.DATE, entry.getDate().getTime());
        values.put(EntryTable.COLS.CONTENT, entry.getContent());

        return values;
    }


}
