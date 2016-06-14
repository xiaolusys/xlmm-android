package com.jimei.xiaolumeimei.ui.activity.main;
import android.view.Menu;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by wisdom on 16/5/9.
 */
public class ComplainWebActivity extends CommonWebViewActivity {

    @Override protected void initViews() {
        super.initViews();
        webviewTitle.setText("投诉建议");
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
