package so.xiaolu.xiaolu.utils;

import java.io.File;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class VolleyApplication extends Application {
    private static final String TAG = "VolleyApplication";

    private RequestQueue mRequestQueue;
    private static VolleyApplication instance;
    private ImageLoader mImageLoader;

    public static VolleyApplication getInstance() {
        Log.d(TAG,"getInstance " + instance);
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        Log.d(TAG,"getImageLoader " + mImageLoader);
        return mImageLoader;
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        super.onCreate();
        Log.d(TAG,"cacheDir "+this.getCacheDir()+" "+"volley");
        File cacheDir = new File(this.getCacheDir(), "volley");
        mRequestQueue = new RequestQueue(new DiskBasedCache(cacheDir), new BasicNetwork(new HurlStack()), 1);

        instance = this;

        MemoryCache mCache = new MemoryCache();
        mImageLoader = new ImageLoader(mRequestQueue, mCache);
        mRequestQueue.start();
    }
}
