package so.xiaolu.xiaolu.utils;

import java.io.File;

import android.app.Application;

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
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        File cacheDir = new File(this.getCacheDir(), "volley");
        mRequestQueue = new RequestQueue(new DiskBasedCache(cacheDir), new BasicNetwork(new HurlStack()), 1);

        instance = this;

        MemoryCache mCache = new MemoryCache();
        mImageLoader = new ImageLoader(mRequestQueue, mCache);
        mRequestQueue.start();
    }
}
