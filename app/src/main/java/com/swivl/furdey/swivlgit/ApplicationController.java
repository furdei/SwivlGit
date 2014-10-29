package com.swivl.furdey.swivlgit;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.swivl.furdey.swivlgit.imagecache.BitmapLruCache;

/**
 * Holds application-wide resources like image cache
 */
public class ApplicationController extends Application {

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
