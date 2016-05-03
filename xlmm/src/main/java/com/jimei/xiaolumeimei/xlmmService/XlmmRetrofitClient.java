package com.jimei.xiaolumeimei.xlmmService;

import android.content.Context;
import android.content.SharedPreferences;

import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.data.XlmmApi;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class XlmmRetrofitClient {

    private static XlmmService mService;
    static SharedPreferences sharedPreferences;

    public static XlmmService getService() {
        if (mService == null) {
            createService();
        }
        return mService;
    }

    private static void createService() {

        mService = createAdapter().create(XlmmService.class);
    }

    private static Retrofit createAdapter() {

        sharedPreferences = XlmmApp.getmContext().getSharedPreferences("", Context.MODE_PRIVATE);

        return new Retrofit.Builder().baseUrl(XlmmApi.APP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(XlmmApp.client)
                .build();
    }

}
