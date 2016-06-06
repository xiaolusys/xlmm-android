package com.jimei.xiaolumeimei.ui.activity.main;

import android.os.Bundle;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;

/**
 * Created by itxuye(http://www.itxuye.com) on 16/4/9.
 */
public class ActivityWebViewActivity extends CommonWebViewActivity {
    private String title;

    @Override
    public void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        title = extras.getString("title", "活动");
    }

    @Override
    protected void initViews() {
        super.initViews();
        webviewTitle.setText(title);
    }
}
