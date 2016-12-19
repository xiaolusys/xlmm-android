package com.jimei.xiaolumeimei.ui.activity.main;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by itxuye(http://www.itxuye.com) on 16/4/9.
 */
public class ActivityWebViewActivity extends CommonWebViewActivity {

    @Override
    public void sharePartyInfo() {
        super.sharePartyInfo();
        MobclickAgent.onEvent(this,"Activity_share");
    }
}
