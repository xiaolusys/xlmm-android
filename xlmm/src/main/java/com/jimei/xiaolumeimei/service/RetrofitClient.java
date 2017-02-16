package com.jimei.xiaolumeimei.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jimei.library.utils.NetUtil;
import com.jimei.xiaolumeimei.BuildConfig;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.okhttp3.PersistentCookieJar;
import com.jimei.xiaolumeimei.okhttp3.cache.SetCookieCache;
import com.jimei.xiaolumeimei.okhttp3.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wisdom on 16/11/23.
 */

public class RetrofitClient {

    private static OkHttpClient mOkHttpClient;
    private static OkHttpClient.Builder builder;

    static {
        builder = initOkHttpClient();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor).addNetworkInterceptor(new StethoInterceptor());
        }
        mOkHttpClient = builder.build();
    }

    public static Retrofit createAdapter() {
        SharedPreferences sharedPreferences = XlmmApp.getmContext().getSharedPreferences("APICLIENT", Context.MODE_PRIVATE);
        String baseUrl;
        if (!TextUtils.isEmpty(sharedPreferences.getString("BASE_URL", ""))) {
            baseUrl = "http://" + sharedPreferences.getString("BASE_URL", "");
        } else {
            baseUrl = XlmmApi.APP_BASE_URL;
        }
        return new Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(mOkHttpClient)
            .build();
    }

    /**
     * 初始化OKHttpClient
     */
    private static OkHttpClient.Builder initOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitClient.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(XlmmApp.getmContext().getCacheDir(), "OkHttpCache"),
                        1024 * 1024 * 100);
                    builder = new OkHttpClient.Builder()
                        .readTimeout(15, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .cache(cache)
                        .addInterceptor(chain -> {
                            Request request = chain.request();
//                                if (JUtils.isNetWorkAvilable()) {
                            request = request.newBuilder()
                                .header("User-Agent", "Android/" + Build.VERSION.RELEASE + " xlmmApp/"
                                    + String.valueOf(BuildConfig.VERSION_CODE) + " Mobile/"
                                    + Build.MODEL + " NetType/" + NetUtil.getNetType(XlmmApp.getmContext()))
                                .build();
//                                } else {
//                                    request = request.newBuilder()
//                                            .cacheControl(CacheControl.FORCE_CACHE)
//                                            .header("User-Agent", "Android/" + Build.VERSION.RELEASE + " xlmmApp/"
//                                                    + String.valueOf(BuildConfig.VERSION_CODE) + " Mobile/"
//                                                    + Build.MODEL + " NetType/" + NetUtil.getNetType(XlmmApp.getmContext()))
//                                            .build();
//                                }
//                                okhttp3.Response response = chain.proceed(request);
//                                if (JUtils.isNetWorkAvilable()) {
//                                    int maxAge = 60 * 60;
//                                    response.newBuilder()
//                                            .removeHeader("Pragma")
//                                            .header("Cache-Control", "public, max-age=" + maxAge)
//                                            .build();
//                                } else {
//                                    int maxStale = 60 * 60 * 24 * 28;
//                                    response.newBuilder()
//                                            .removeHeader("Pragma")
//                                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                            .build();
//                                }
                            return chain.proceed(request);
                        })
                        .cookieJar(new PersistentCookieJar(new SetCookieCache(),
                            new SharedPrefsCookiePersistor(XlmmApp.getmContext())));
                }
            }
        }
        return builder;
    }

}
