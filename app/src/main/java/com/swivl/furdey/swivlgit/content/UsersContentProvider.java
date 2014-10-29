package com.swivl.furdey.swivlgit.content;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Content provider for users data in a local cache
 */
public class UsersContentProvider extends ContentProvider {

    public static final String LOCAL_ID = "_id";
    public static final String GIT_ID = "id";
    public static final String LOGIN = "login";
    public static final String AVATAR_URL = "avatar_url";
    public static final String HTML_URL = "html_url";

    private static final String USERS_TABLE_NAME = "users";

    private static final String AUTHORITY = UsersContentProvider.class.getCanonicalName();
    private static final String TAG       = UsersContentProvider.class.getCanonicalName();

    public static final  Uri        USERS_URI         = Uri.parse("content://" + AUTHORITY + "/" +
                                                        USERS_TABLE_NAME);
    private static final UriMatcher sUriMatcher       = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int        ALL_USERS         = 1;

    static {
        sUriMatcher.addURI(AUTHORITY, USERS_TABLE_NAME, ALL_USERS);
    }

    @Override
    public boolean onCreate() {
        DatabaseHelper.init(getContext().getApplicationContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (sUriMatcher.match(uri) != ALL_USERS) {
            throw new IllegalArgumentException("uri: " + uri.toString());
        }

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(USERS_TABLE_NAME);
        Cursor cursor = builder.query(DatabaseHelper.getInstance().getWritableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = DatabaseHelper.getInstance().getWritableDatabase().insert(USERS_TABLE_NAME, null, values);
        Uri insertedUri = ContentUris.withAppendedId(USERS_URI, id);
        return insertedUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return DatabaseHelper.getInstance().getWritableDatabase().delete(USERS_TABLE_NAME,
                selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    public static void createTable(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ").append(USERS_TABLE_NAME).append(" ( ");
        builder.append(LOCAL_ID  ).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        builder.append(GIT_ID    ).append(" INTEGER NOT NULL, ");
        builder.append(LOGIN     ).append(" TEXT NOT NULL, ");
        builder.append(AVATAR_URL).append(" TEXT NOT NULL, ");
        builder.append(HTML_URL  ).append(" TEXT NOT NULL  ");
        builder.append(");");

        db.execSQL(builder.toString());
    }
}
