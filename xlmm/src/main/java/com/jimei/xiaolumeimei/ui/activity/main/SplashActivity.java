package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.StartBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import okhttp3.Call;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SplashActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.imageView_startup)
    ImageView imageView_startup;


    //@Bind(R.id.rl_splash)  RelativeLayout rl_splash;

    @Override
    protected void initViews() {

    /*ImageView view = new ImageView(this);
    view.setBackgroundResource(R.drawable.loading);
    setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));*/

        Window window = getWindow();
        //4.4版本及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //5.0版本及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

//    new Timer().schedule(new TimerTask() {
//      @Override public void run() {
//        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        finish();
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//      }
//    }, 2000);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

        ActivityModel.getInstance()
                .getStarsBean()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<StartBean>() {
                    @Override
                    public void onNext(StartBean startBean) {
                        if (null != startBean && !TextUtils.isEmpty(startBean.getPicture())) {
                            try {
                                OkHttpUtils.get()
                                        .url(startBean.getPicture())
                                        .build()
                                        .execute(new BitmapCallback() {
                                            @Override
                                            public void onError(Call call, Exception e) {
                                                e.printStackTrace();
                                            }

                                            @Override
                                            public void onResponse(Bitmap response) {
                                                imageView_startup.setImageBitmap(response);

                                                new Timer().schedule(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        startActivity(
                                                                new Intent(SplashActivity.this, MainActivity.class));
                                                        finish();
                                                        overridePendingTransition(android.R.anim.fade_in,
                                                                android.R.anim.fade_out);
                                                    }
                                                }, 2000);
                                            }
                                        });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    finish();
                                    overridePendingTransition(android.R.anim.fade_in,
                                            android.R.anim.fade_out);
                                }
                            }, 2000);
                        }
                    }
                });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
