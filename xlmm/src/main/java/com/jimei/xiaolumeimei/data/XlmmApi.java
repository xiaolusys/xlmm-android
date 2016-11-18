package com.jimei.xiaolumeimei.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jimei.xiaolumeimei.XlmmApp;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class XlmmApi {
    public static final String APP_BASE_URL = "https://m.xiaolumeimei.com";
    public static final String QINIU_UPLOAD_URL_BASE =
            "http://7xkyoy.com2.z0.glb.qiniucdn.com/";

    public static String getAppUrl() {
        SharedPreferences preferences = XlmmApp.getmContext().getSharedPreferences("APICLIENT", Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(preferences.getString("BASE_URL", ""))) {
            return "https://" + preferences.getString("BASE_URL", "");
        }
        return XlmmApi.APP_BASE_URL;
    }
}
