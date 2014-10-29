package com.swivl.furdey.swivlgit.content;

import android.net.Uri;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default and the only one implementation for cache
 */
public class CacheImpl implements Cache {

    private final long ttl;
    private final Map<Uri, Long> cache;

    public CacheImpl() {
        this(DEFAULT_TTL);
    }

    public CacheImpl(long ttl) {
        this.ttl = ttl;
        this.cache = new ConcurrentHashMap<Uri, Long>();
    }

    @Override
    public boolean isCached(Uri uri) {
        Long cachedTime = cache.get(uri);
        return cachedTime != null && (System.currentTimeMillis() - cachedTime <= ttl);
    }

    @Override
    public void setCached(Uri uri) {
        cache.put(uri, System.currentTimeMillis());
    }
}
