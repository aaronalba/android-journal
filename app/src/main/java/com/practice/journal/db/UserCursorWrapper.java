/**
 * Class for wrapping the Cursor returned by the query to the SQLite database that will make the
 * retrieval of users more convenient.
 * @author Aaron Alba
 */

package com.practice.journal.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.practice.journal.models.Entry;
import com.practice.journal.models.User;

import java.util.Date;

public class UserCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public User getUser() {
        String nameData = getString(getColumnIndex(DatabaseSchema.UserTable.COLS.NAME));
        String pinData = getString(getColumnIndex(DatabaseSchema.UserTable.COLS.PIN));

        // create the user
        User user = new User(nameData, pinData);
        return user;
    }
}