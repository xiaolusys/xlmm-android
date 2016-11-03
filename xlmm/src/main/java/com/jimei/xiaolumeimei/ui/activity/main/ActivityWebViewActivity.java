package com.jimei.xiaolumeimei.ui.activity.main;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by itxuye(http://www.itxuye.com) on 16/4/9.
 */
public class ActivityWebViewActivity extends CommonWebViewActivity {

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

    @Override
    public void sharePartyInfo() {
        super.sharePartyInfo();
        MobclickAgent.onEvent(this,"Activity_share");
    }

}
