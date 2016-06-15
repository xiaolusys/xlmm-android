package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.Menu;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/25.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class FansWebViewActivity extends CommonWebViewActivity {

    @Override
    protected void initViews() {
        super.initViews();
        webviewTitle.setText("我的粉丝");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}