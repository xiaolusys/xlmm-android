package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.bumptech.glide.Glide;
import com.jimei.library.rx.RxCountDown;
import com.jimei.library.utils.NetUtil;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.umeng.analytics.MobclickAgent;

import rx.Subscription;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SplashActivity extends AppCompatActivity {

    private Subscription mSubscribe;
    private int mWidthPixels;
    private int mHeightPixels;
    private String mPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MobclickAgent.setDebugMode(true);
        ViewUtils.setWindowStatus(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mWidthPixels = displayMetrics.widthPixels;
        mHeightPixels = displayMetrics.heightPixels;
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", getPackageName()));
        mSubscribe = ActivityModel.getInstance()
                .getStartAds()
                .subscribe(startBean -> {
                    if (startBean != null) {
                        mPicture = startBean.getPicture();
                        if (startBean.getPicture() != null && !"".equals(startBean.getPicture())) {
                            Glide.with(SplashActivity.this).load(startBean.getPicture()).downloadOnly(mWidthPixels, mHeightPixels);
                        }
                    }
                }, Throwable::printStackTrace);
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
        if (NetUtil.isNetworkAvailable(this) && mPicture != null && !mPicture.equals("")) {
            Intent intent = new Intent(SplashActivity.this, AdvertisementActivity.class);
            intent.putExtra("link", mPicture);
            startActivity(intent);
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
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
