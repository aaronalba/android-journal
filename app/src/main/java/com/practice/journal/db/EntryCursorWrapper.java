/**
 * Class for wrapping the Cursor returned by the query to the SQLite database. This will make the
 * retrieval of the Entry object from the Cursor easier.
 * @author Aaron Alba
 */

package com.practice.journal.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.practice.journal.models.Entry;
import com.practice.journal.db.DatabaseSchema.EntryTable;

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
        // get the values from the cursor using the column indices
        String titleData = getString(getColumnIndex(EntryTable.COLS.TITLE));
        String uuidData = getString(getColumnIndex(EntryTable.COLS.UUID));
        long dateData = getLong(getColumnIndex(EntryTable.COLS.DATE));
        String contentData = getString(getColumnIndex(EntryTable.COLS.CONTENT));

        // create the entry
        Entry entry = new Entry(uuidData);
        entry.setTitle(titleData);
        entry.setDate(new Date(dateData));
        entry.setContent(contentData);

        return entry;
    }
}
