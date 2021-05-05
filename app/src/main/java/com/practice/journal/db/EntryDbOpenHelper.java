/**
 * This class is an SQLiteOpenHelper that is responsible for creating, reading and updating the database.
 * @author Aaron Alba
 */

package com.practice.journal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.practice.journal.db.EntryDbSchema.EntryTable;

public class EntryDbOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "entryDatabase.db";

    /**
     * Class constructor for creating this Database Open Helper object.
     * @param context The application's context. Used for locating the path of this database in the app's folder
     */
    public EntryDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * The method to be called when the database is not yet created in the app's folder
     * @param db Reference to the database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EntryTable.NAME + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EntryTable.COLS.UUID + ", "
                + EntryTable.COLS.TITLE + ", "
                + EntryTable.COLS.DATE + ", "
                + EntryTable.COLS.CONTENT
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No implementation yet since there is only one version of the database
    }
}
