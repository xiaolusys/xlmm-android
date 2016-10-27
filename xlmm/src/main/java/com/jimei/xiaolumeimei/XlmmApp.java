package com.jimei.xiaolumeimei;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.receiver.mipush.XiaoMiMessageReceiver;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.List;

/**
 * Created by ye.xu on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reser.
 */
public class XlmmApp extends MultiDexApplication {

    private static Context mContext;
    private static XiaoMiMessageReceiver.XiaoMiPushHandler handler = null;

    public static Context getmContext() {
        return mContext;
    }

    public static Context getInstance() {
        return mContext;
    }

    public static XiaoMiMessageReceiver.XiaoMiPushHandler getHandler() {
        return handler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Thread.setDefaultUncaughtExceptionHandler(new MyUnCaughtExceptionHandler());
        mContext = getApplicationContext();
        Stetho.initializeWithDefaults(this);
        JUtils.initialize(this);
        JUtils.setDebug(false, "xlmm");
        AutoLayoutConifg.getInstance().useDeviceSize();
        //初始化push推送服务
        if (shouldInit()) {
            JUtils.Log("XlmmApp", "reg xiaomi push");
            MiPushClient.registerPush(getApplicationContext(), XlmmConst.XIAOMI_APP_ID,
                    XlmmConst.XIAOMI_APP_KEY);
        }
        if (handler == null) {
            handler = new XiaoMiMessageReceiver.XiaoMiPushHandler(getApplicationContext());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    //异常退出的时候,自动重启
    class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            Intent intent = new Intent(XlmmApp.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            XlmmApp.this.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
}
