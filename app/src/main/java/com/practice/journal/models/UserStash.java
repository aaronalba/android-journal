/**
 * Class that serves as an interface between the app and the database for storing and retrieving
 * users. Currently I will only implement one user since that will require me to rewrite the CRUD
 * for the Entries stored in the Database. The user for now will only be used for authentication
 * and to protect the Entries by providing first a PIN before starting the application.
 * Though this class is planned to be extended for multi-user usage.
 * @author Aaron Alba.
 */

package com.practice.journal.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.practice.journal.db.DatabaseOpenHelper;
import com.practice.journal.db.DatabaseSchema;
import com.practice.journal.db.UserCursorWrapper;

import static com.practice.journal.db.DatabaseSchema.*;

public class UserStash {
    // Singleton instance of this class
    private static UserStash sUserStash;

    // the instance of the application database
    private SQLiteDatabase mDatabase;

    // private constructor ensure only one instance of this class is available to the app
    private UserStash(Context context) {
        mDatabase = DatabaseOpenHelper.getDatabase(context);
    }


    /**
     * Returns the only instance of this UserStash
     * @param context The application context.
     * @return  the instance of this UserStash.
     */
    public static UserStash get(Context context) {
        if (sUserStash==null)
            sUserStash = new UserStash(context);
        return sUserStash;
    }


    /**
     * Returns a boolean value whether the given username is registered in the app's database.
     * @return true or false is the given username is in the app.
     */
    public boolean hasUser(String name) {
        // query the database
        Cursor cursor = mDatabase.query(
                UserTable.NAME,
                null,
                UserTable.COLS.NAME + "= ?",
                new String[] {name},
                null,
                null,
                null
        );

        // wrap the cursor
        UserCursorWrapper userCursor = new UserCursorWrapper(cursor);

        // check if the cursor returned results
        if (userCursor.getCount() == 0) {
            return false;
        }

        return true;
    }


    /**
     * Queries the database to check if the given pin exists for a given username.
     * @param name  the username of the User
     * @param pin the pin of the User
     * @return  true or false
     */
    public boolean authenticate(String name, String pin) {
        // get the pin of the stated user name
        String userPin = getPin(name);

        // check for equality
        if (pin.equals(userPin)) {
            return true;
        }
        return false;
    }


    /**
     * Adds the given user to the application database.
     * @param user The User object to be added.
     */
    public void createUser(User user) {
        ContentValues userValues = getContentValues(user);
        mDatabase.insert(UserTable.NAME, null, userValues);
    }


    /**
     * Updates the user with a new pin.
     * @param user The User object containing the new data.
     */
    public void updatePin(User user) {
        // the username of the user to be updated
        String username = user.getName();

        // wrap the user to a content value object
        ContentValues values = getContentValues(user);

        // database update query
        mDatabase.update(
                UserTable.NAME,
                values,
                UserTable.COLS.NAME + "= ?",
                new String[] { username });
    }


    public void deleteUser(User user) {
        // the username of the user to be deleted
        String username = user.getName();

        // database delete query
        mDatabase.delete(
                UserTable.NAME,
                UserTable.COLS.NAME + "= ?",
                new String[] { username });
    }


    /*
        ContentValues object containing the data from the given User object. This ContentValues object
        will be used for database operations.
     */
    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserTable.COLS.NAME, user.getName());
        values.put(UserTable.COLS.PIN, user.getPin());
        return values;
    }


    /*
        Returns the pin of the given username
     */
    private String getPin(String name) {
        // query the database
        Cursor cursor = mDatabase.query(
                UserTable.NAME,
                null,
                UserTable.COLS.NAME + "= ?",
                new String[] {name},
                null,
                null,
                null
        );

        // wrap the cursor
        UserCursorWrapper userCursor = new UserCursorWrapper(cursor);

        // check if the cursor returned results
        if (userCursor.getCount() == 0) {
            return null;
        }

        // move the cursor to the first item and return the pin
        try {
            cursor.moveToFirst();
            return userCursor.getUser().getPin();
        } finally {
            cursor.close();
        }
    }
}
