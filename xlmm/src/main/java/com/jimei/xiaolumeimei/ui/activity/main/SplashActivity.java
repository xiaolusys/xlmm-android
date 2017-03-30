package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jimei.library.rx.RxCountDown;
import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.NetUtil;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.entities.StartBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.util.LoginUtils;
import com.umeng.analytics.MobclickAgent;

import rx.Subscription;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SplashActivity extends AppCompatActivity {

    private Subscription mSubscribe;
    private String mPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MobclickAgent.setDebugMode(true);
        ViewUtils.setWindowStatus(this);
        PackageManager pm = getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
            pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", getPackageName()));
        mSubscribe = XlmmApp.getActivityInteractor(this)
            .getStartAds(new ServiceResponse<StartBean>() {
                @Override
                public void onNext(StartBean startBean) {
                    mPicture = startBean.getPicture();
                    if (mPicture != null && !"".equals(mPicture)) {
                        Glide.with(SplashActivity.this).load(mPicture).downloadOnly(
                            JUtils.getScreenWidth(), JUtils.getScreenHeightWithStatusBar());
                    }
                }
            });
        if (permission) {
            RxCountDown.countdown(2).subscribe(integer -> {
                if (integer == 0) {
                    jumpToAds();
                }
            }, throwable -> jumpToAds());
        } else {
            new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("应用包含缓存节省流量功能,需要打开存储权限,应用才能正常使用。")
                .setPositiveButton("确认", (dialog, which) -> {
                    dialog.dismiss();
                    getAppDetailSettingIntent();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                }).show();
        }
    }

    private void jumpToAds() {
//        mPicture="http://c.hiphotos.baidu.com/image/pic/item/d62a6059252dd42a6a943c180b3b5bb5c8eab8e7.jpg";
        if (NetUtil.isNetworkAvailable(this) && mPicture != null && !mPicture.equals("")) {
            Intent intent = new Intent(SplashActivity.this, AdvertisementActivity.class);
            intent.putExtra("link", mPicture);
            startActivity(intent);
        } else {
            if (LoginUtils.checkLoginState(this)) {
                startActivity(new Intent(SplashActivity.this, TabActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSubscribe != null && mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }
}
