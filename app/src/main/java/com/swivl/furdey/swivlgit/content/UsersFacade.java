package com.swivl.furdey.swivlgit.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.swivl.furdey.swivlgit.content.model.User;

/**
 * Interface (Facade) class to deal with users data
 */
public class UsersFacade {

    private static Cache cache = new CacheImpl();

    public static ContentValues getContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersContentProvider.LOCAL_ID, user.getLocalId());
        contentValues.put(UsersContentProvider.GIT_ID, user.getGitId());
        contentValues.put(UsersContentProvider.LOGIN, user.getLogin());
        contentValues.put(UsersContentProvider.AVATAR_URL, user.getAvatarUrl(User.DEFAULT_SIZE));
        contentValues.put(UsersContentProvider.HTML_URL, user.getHtmlUrl());
        return contentValues;
    }

    public static ContentValues[] getContentValues(User[] users) {
        if (users == null || users.length == 0) {
            return null;
        }

        int usersCount = users.length;
        ContentValues[] contentValues = new ContentValues[usersCount];

        for (int i = 0; i < usersCount; i++) {
            contentValues[i] = getContentValues(users[i]);
        }

        return contentValues;
    }

    public static User fromCursor(Cursor cursor) {
        int localIdIndex = cursor.getColumnIndex(UsersContentProvider.LOCAL_ID);
        int gitIdIndex = cursor.getColumnIndex(UsersContentProvider.GIT_ID);
        int loginIndex = cursor.getColumnIndex(UsersContentProvider.LOGIN);
        int avatarUrlIndex = cursor.getColumnIndex(UsersContentProvider.AVATAR_URL);
        int htmlUrlIndex = cursor.getColumnIndex(UsersContentProvider.HTML_URL);

        User user = new User();

        if (localIdIndex >= 0) {
            user.setLocalId(cursor.getLong(localIdIndex));
        }

        if (gitIdIndex >= 0) {
            user.setGitId(cursor.getLong(gitIdIndex));
        }

        if (loginIndex >= 0) {
            user.setLogin(cursor.getString(loginIndex));
        }

        if (avatarUrlIndex >= 0) {
            user.setAvatarUrl(cursor.getString(avatarUrlIndex));
        }

        if (htmlUrlIndex >= 0) {
            user.setHtmlUrl(cursor.getString(htmlUrlIndex));
        }

        return user;
    }

    public static CursorLoader getUsers(Context context) {
        if (!cache.isCached(UsersContentProvider.USERS_URI)) {
            // there is no data in cache or cache is expired
            context.startService(UsersService.getUsersIntent(context));
        }

        String[] projection = new String[] {
                UsersContentProvider.LOCAL_ID,
                UsersContentProvider.GIT_ID,
                UsersContentProvider.LOGIN,
                UsersContentProvider.AVATAR_URL,
                UsersContentProvider.HTML_URL };

        return new CursorLoader(context, UsersContentProvider.USERS_URI,
                projection, null, null, null);
    }

    public static int insertUsers(Context context, User[] users) {
        int inserted = context.getContentResolver().bulkInsert(UsersContentProvider.USERS_URI,
                getContentValues(users));
        cache.setCached(UsersContentProvider.USERS_URI);
        context.getContentResolver().notifyChange(UsersContentProvider.USERS_URI, null);
        return inserted;
    }

    public static int deleteAllUsers(Context context) {
        return context.getContentResolver().delete(UsersContentProvider.USERS_URI, null, null);
    }

}
