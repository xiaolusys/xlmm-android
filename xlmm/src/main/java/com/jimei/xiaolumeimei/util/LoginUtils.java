package com.jimei.xiaolumeimei.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import com.jimei.library.utils.DataClearManager;
import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.entities.UserAccountBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class LoginUtils {
    private static String TAG = "LoginUtils";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences sharedPreferences1;
    private static SharedPreferences sharedPreferences2;
    private static SharedPreferences sharedPreferences3;
    private static SharedPreferences sharedPreferences4;
    private static SharedPreferences sharedPreferences5;
    private static SharedPreferences.Editor editor;
    private static SharedPreferences.Editor editor1;
    private static SharedPreferences.Editor editor2;
    private static SharedPreferences.Editor editor3;
    private static SharedPreferences.Editor editor4;
    private static SharedPreferences.Editor editor5;

    public static void saveLoginInfo(boolean isSuccess, Context context, String username,
                                     String password) {
        sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("name", username);
        editor.putString("password", password);
        editor.putBoolean("success", isSuccess);
        editor.apply();
        setMamaInfo(context);
        Log.d(TAG, "save logininfo ");
    }

    public static void saveLoginSuccess(boolean isSuccess, Context context) {
        sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("success", isSuccess);
        editor.apply();
        setMamaInfo(context);
        Log.d(TAG, "save logininfo success");
    }

    public static void setMamaInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("mama_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        XlmmApp.getMainInteractor(context)
            .getProfile(new ServiceResponse<UserInfoBean>() {
                @Override
                public void onNext(UserInfoBean userInfoBean) {
                    if (userInfoBean != null && userInfoBean.getXiaolumm() != null
                        && userInfoBean.getXiaolumm().getId() != 0) {
                        edit.putBoolean("success", true);
                        edit.apply();
                    }
                }
            });
    }

    public static boolean getMamaInfo(Context context) {
        SharedPreferences shared = context.getSharedPreferences("mama_info", Context.MODE_PRIVATE);
        return shared.getBoolean("success", false);
    }

    public static void delLoginInfo(Context context) {
        delUserAccount(context, null);
        sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);
        sharedPreferences1 = context.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        sharedPreferences3 = context.getSharedPreferences("CookiePersistence", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        editor1 = sharedPreferences1.edit();
        editor1.clear();
        editor1.apply();

        editor3 = sharedPreferences3.edit();
        editor3.clear();
        editor3.apply();
        Log.d(TAG, "clear logininfo ");
        SharedPreferences shared = context.getSharedPreferences("mama_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.clear();
        edit.apply();
    }

    //获取用户信息
    public static String[] getLoginInfo(Context context) {

        sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        boolean success = sharedPreferences.getBoolean("success", false);

        return new String[]{username, password, success + ""};
    }

    //获取用户登录状态
    public static boolean checkLoginState(Context context) {

        sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);

        boolean success = sharedPreferences.getBoolean("success", false);

        return success;
    }

    public static void setPushUserAccount(Context context, String mRegId) {
        try {
            String android_id = Settings.Secure.getString(XlmmApp.getmContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
            JUtils.Log("regid", android_id);
            XlmmApp.getUserInteractor(context)
                .getUserAccount("android", mRegId,
                    Settings.Secure.getString(XlmmApp.getmContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID), new ServiceResponse<UserAccountBean>() {
                        @Override
                        public void onNext(UserAccountBean user) {
                            saveMipushOk(context, true);
                            JUtils.Log(TAG, "UserAccountBean:, " + user.toString());
                            if ((getUserAccount(context) != null)
                                && ((!getUserAccount(context).isEmpty()))
                                && (!getUserAccount(context).equals(user.getUserAccount()))) {

                                MiPushClient.unsetUserAccount(context.getApplicationContext(),
                                    getUserAccount(context), null);
                                JUtils.Log(TAG, "unset useraccount: " + getUserAccount(context));
                            }
                            MiPushClient.setUserAccount(context.getApplicationContext(), user.getUserAccount(),
                                null);
                        }

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Log.e(TAG, "error: getUserAccount" + e.getLocalizedMessage());
                            super.onError(e);
                            deleteIsMipushOk(context);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveMipushOk(Context context, boolean isOk) {
        sharedPreferences5 = context.getSharedPreferences("login_info_mipushok", Context.MODE_PRIVATE);
        editor5 = sharedPreferences5.edit();
        editor5.putBoolean("ismipush", isOk);
        editor5.apply();
    }

    public static boolean isMipushOk(Context context) {
        sharedPreferences5 = context.getSharedPreferences("login_info_mipushok", Context.MODE_PRIVATE);
        return sharedPreferences5.getBoolean("ismipush", false);
    }

    public static void deleteIsMipushOk(Context context) {
        sharedPreferences5 = context.getSharedPreferences("login_info_mipushok", Context.MODE_PRIVATE);
        editor5 = sharedPreferences5.edit();
        editor5.clear();
        editor5.apply();
    }

    public static void saveUserAccount(Context context, String userAccount) {
        sharedPreferences4 = context.getSharedPreferences("login_info_mipush", Context.MODE_PRIVATE);
        editor4 = sharedPreferences4.edit();
        editor4.putString("userAccount", userAccount);
        editor4.apply();
        Log.d(TAG, "save saveUserAccount ");
    }

    public static void delUserAccount(Context context, String userAccount) {
        sharedPreferences4 = context.getSharedPreferences("login_info_mipush", Context.MODE_PRIVATE);
        editor4 = sharedPreferences4.edit();
        editor4.putString("userAccount", "");
        editor4.apply();
        Log.d(TAG, "delUserAccount ");
    }

    public static String getUserAccount(Context context) {

        sharedPreferences4 = context.getSharedPreferences("login_info_mipush", Context.MODE_PRIVATE);

        String account = sharedPreferences4.getString("userAccount", "");

        return account;
    }

    public static void clearCacheEveryWeek(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int i = calendar.get(Calendar.WEEK_OF_YEAR);
        SharedPreferences preferences = context.getSharedPreferences("clear_cache", Context.MODE_PRIVATE);
        int flag = preferences.getInt("flag", -1);
        if (i != flag) {
            SharedPreferences.Editor edit = preferences.edit();
            edit.putInt("flag", i);
            edit.apply();
            DataClearManager.cleanApplicationData(XlmmApp.getInstance());
        }
    }
}
