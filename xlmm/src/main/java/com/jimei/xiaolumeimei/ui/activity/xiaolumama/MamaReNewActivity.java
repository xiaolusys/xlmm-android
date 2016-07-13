package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.Menu;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by wisdom on 16/7/13.
 */
public class MamaReNewActivity extends CommonWebViewActivity {
    @Override
    protected void initViews() {
        super.initViews();
        webviewTitle.setText("妈妈续费");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
