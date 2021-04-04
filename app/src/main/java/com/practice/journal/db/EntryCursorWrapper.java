/**
 * Class for wrapping the Cursor returned by the query to the SQLite database. This will make the
 * retrieval of the Entry object from the Cursor easier.
 * @author Aaron Alba
 */

package com.practice.journal.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.practice.journal.Entry;

import java.util.Date;

public class EntryCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     * @param cursor The underlying cursor to wrap.
     */
    public EntryCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    /**
     * Returns an Entry object created from the data retrieved from this cursor.
     * @return The Entry object from the cursor.
     */
    public Entry getEntry() {
        // get the column indices of the data in the Cursor
        int titleColumn = this.getColumnIndex(EntryDbSchema.EntryTable.COLS.TITLE);
        int uuidColumn = this.getColumnIndex(EntryDbSchema.EntryTable.COLS.UUID);
        int dateColumn = this.getColumnIndex(EntryDbSchema.EntryTable.COLS.DATE);
        int contentColumn = this.getColumnIndex(EntryDbSchema.EntryTable.COLS.CONTENT);

        // get the values from the cursor using the column indices
        String titleData = this.getString(titleColumn);
        String uuidData = this.getString(uuidColumn);
        long dateData = this.getLong(dateColumn);
        String contentData = this.getString(contentColumn);

        // create the entry
        Entry entry = new Entry(uuidData);
        entry.setTitle(titleData);
        entry.setDate(new Date(dateData));
        entry.setContent(contentData);

        return entry;
    }
}
