package com.swivl.furdey.swivlgit.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Responsible for creating and upgrading a local cache database
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "swivlgit.db";
    private static final int DATABASE_VERSION = 1;

    private static volatile DatabaseHelper helper;

    public static void init(Context context) {
        if (helper == null) {
            synchronized (DatabaseHelper.class) {
                if (helper == null) {
                    helper = new DatabaseHelper(context);
                }
            }
        }
    }

    public static DatabaseHelper getInstance() {
        return helper;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        UsersContentProvider.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
