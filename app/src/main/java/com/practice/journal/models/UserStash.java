/**
 * Class that serves as an interface between the app and the database for storing and retrieving
 * users. Currently I will only implement one user since that will require me to rewrite the CRUD
 * for the Entries stored in the Database. The user for now will only be used for authentication
 * and to protect the Entries by providing first a PIN before starting the application.
 * Though this class is planned to be extended for multi-user usage.
 * @author Aaron Alba.
 */

package com.practice.journal.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UserStash {
    // Singleton instance of this class
    private static UserStash sUserStash;

    private SQLiteDatabase mDatabase;


    // private constructor ensure only one instance of this class is available to the app
    private UserStash() {
        // TODO: construct database instance here
    }


    /**
     * Returns the only instance of this UserStash
     * @param context The application context.
     * @return  the instance of this UserStash.
     */
    public static UserStash get(Context context) {
        if (sUserStash==null)
            sUserStash = new UserStash();
        return sUserStash;
    }



}
