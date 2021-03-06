/**
 * Class that serves as an interface to the list of entries being maintained by the Journal App.
 * This class follows a singleton pattern to avoid problems in having multiple instances of the
 * list of journal entries.
 * @author Aaron Alba
 */

package com.practice.journal.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.practice.journal.db.EntryCursorWrapper;
import com.practice.journal.db.DatabaseOpenHelper;
import com.practice.journal.db.DatabaseSchema.EntryTable;

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
        mDatabase = DatabaseOpenHelper.getDatabase(context);
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
     * Returns the list of journal entries to be used by the RecyclerView for listing the Entries
     * @return List of entries
     */
    public List<Entry> getEntries() {
        // create the list the will hold the Entry objects
        List<Entry> list = new ArrayList<>();

        // SELECT all of the rows in the database by passing null to the whereClause
        EntryCursorWrapper cursor = queryEntries(null, null);

        try {
            // move the cursor to the first entry
            cursor.moveToFirst();

            // iterate over each entry
            while(!cursor.isAfterLast()) {
                list.add(cursor.getEntry()); // add the Entry to the list

                cursor.moveToNext();    // move to the next entry
            }
        } finally {
            cursor.close();
        }

        return list;
    }


    /**
     * Adds an entry to the database of journal entries.
     * @param entry The entry to be added to the database.
     */
    public void addEntry(Entry entry) {
        // create the content values from the given entry
        ContentValues data = getContentValues(entry);

        // insert data to the database
        mDatabase.insert(EntryTable.NAME, null, data);
    }


    /**
     * Updates an entry in the database of journal entries.
     * @param id The id of the Entry to be updated.
     * @param entry The entry containing the updated data
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
     * Removes an entry in the database of journal entries.
     * @param id The id of the Entry to be deleted.
     */
    public void deleteEntry(UUID id) {
        // get the id of the entry to be deleted
        String uuidString = id.toString();

        // run the delete operation on the database
        mDatabase.delete(EntryTable.NAME,
                EntryTable.COLS.UUID + " = ?",
                new String[] { uuidString });
    }


    /**
     * Returns an entry from the database of journal entries.
     * @param id The id of the entry to be retrieved.
     * @return The matching Entry object.
     */
    public Entry getEntry(UUID id) {
        // convert uuid to string
        String uuidString = id.toString();

        // query the database
        EntryCursorWrapper cursorWrapper = queryEntries(EntryTable.COLS.UUID + " = ?", new String[] { uuidString });

        // check if the cursor has some items
        if (cursorWrapper.getCount() == 0) {
            return null;
        }

        // move the cursor to the first item
        try {
            // move the cursor to the first entry that matched, which is the only entry in this cursor
            cursorWrapper.moveToFirst();
            // return the Entry object
            return cursorWrapper.getEntry();

        } finally {
            cursorWrapper.close();
        }

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


    /*
        This method returns the cursor pointing to the selected database entry
        @param whereClause Tells which row of the Table will be returned
        @param whereArgs Argument string for the whereClause
        @return The cursor containing the selected rows.
     */
    private EntryCursorWrapper queryEntries(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                EntryTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new EntryCursorWrapper(cursor);
    }
}
