package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.Menu;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by wisdom on 16/8/22.
 */
public class TeamExplainActivity extends CommonWebViewActivity {
    @Override
    protected void initViews() {
        super.initViews();
        webviewTitle.setText("团队说明");
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