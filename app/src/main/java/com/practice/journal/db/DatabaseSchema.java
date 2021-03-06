/**
 * Class for defining the Schema that will be used in the database. This class contains static
 * Strings containing the names of each table in the database and the names of the columns
 * contained in each table.
 * @author Aaron Alba
 */

package com.practice.journal.db;

public final class DatabaseSchema {
    // private constructor to avoid instantiation of this class
    private DatabaseSchema() {}

    // table 1 - Entry Table
    public static final class EntryTable {
        public static final String NAME = "entry";
        public static final class COLS {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String CONTENT = "content";
        }
    }

    // table 2 - User Table
    public static final class UserTable {
        public static final String NAME = "user";
        public static final class COLS {
            public static final String NAME = "username";
            public static final String PIN = "pin";
        }
    }
}
