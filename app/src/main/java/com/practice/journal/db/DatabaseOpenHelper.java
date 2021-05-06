/**
 * This class is an SQLiteOpenHelper that is responsible for creating, reading and updating the database.
 * This class now also maintains the single instance of the database for this application.
 * @author Aaron Alba
 */

package com.practice.journal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.practice.journal.db.DatabaseSchema.EntryTable;

public final class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase sDatabase;
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "journal_database.db";

    /**
     * Class constructor for creating this Database Open Helper object.
     * @param context The application's context. Used for locating the path of this database in the app's folder
     */
    private DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Returns an instance of the SQLite Database for this application.
     * @param context The application context.
     * @return instance of the SQLite database.
     */
    public static SQLiteDatabase getDatabase(Context context) {
        if (sDatabase == null) {
            sDatabase = new DatabaseOpenHelper(context).getWritableDatabase();
        }
        return sDatabase;
    }



    /**
     * The method to be called when the database is not yet created in the app's folder
     * @param db Reference to the database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create Entry table
        db.execSQL("CREATE TABLE " + EntryTable.NAME + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EntryTable.COLS.UUID + ", "
                + EntryTable.COLS.TITLE + ", "
                + EntryTable.COLS.DATE + ", "
                + EntryTable.COLS.CONTENT
                + ")"
        );


        // create User table
        db.execSQL("CREATE TABLE " + EntryTable.NAME + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseSchema.UserTable.COLS.NAME + ", "
                + DatabaseSchema.UserTable.COLS.NAME + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No implementation yet since there is only one version of the database
    }
}
