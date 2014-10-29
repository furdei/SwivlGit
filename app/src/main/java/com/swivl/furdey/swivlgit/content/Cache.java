package com.swivl.furdey.swivlgit.content;

import android.net.Uri;

/**
 * Cache used to determine whether user data in local database are fresh or not
 */
public interface Cache {

    /**
     * Default cache time to live is 5 minutes
     */
    public static long DEFAULT_TTL = 300000;

    boolean isCached(Uri uri);

    void setCached(Uri uri);
}
