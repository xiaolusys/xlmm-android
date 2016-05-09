package com.jimei.xiaolumeimei.xlmmService;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jude.utils.JUtils;

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

        sharedPreferences = XlmmApp.getmContext().getSharedPreferences("APICLIENT", Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(sharedPreferences.getString("BASE_URL", ""))) {

            String baseUrl ="http://"+ sharedPreferences.getString("BASE_URL", "");

            JUtils.Log("InformationActivity","baseurl===="+baseUrl);
            return new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(XlmmApp.client)
                    .build();
        }

        JUtils.Log("InformationActivity","baseurl====");
        return new Retrofit.Builder().baseUrl(XlmmApi.APP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(XlmmApp.client)
                .build();
    }

}
