package com.beatutify;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.beatutify.util.AppCommons;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by karan.kalsi on 2/27/2018.
 */

public class BeautifyApplication extends MultiDexApplication {


    public static final String TAG = BeautifyApplication.class
            .getSimpleName();
    private RequestQueue mRequestQueue;
    private static BeautifyApplication mInstance;

    private static final int MAX_SIZE = 100 * 1024 * 1024;
    private DisplayImageOptions imageOptions;

    private void initImageLoader(Context context) {
        imageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.placeholder_rect)
                .showImageOnFail(R.drawable.placeholder_rect)
                .showImageOnLoading(R.drawable.placeholder_rect)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(MAX_SIZE))
                .defaultDisplayImageOptions(imageOptions)
                .diskCache(new UnlimitedDiskCache(new File(AppCommons.getAppHiddenDirectory(context))))
//                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mInstance = this;
        initImageLoader(this);
    }
    public static synchronized BeautifyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
