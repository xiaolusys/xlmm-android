package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import cn.sharesdk.framework.Platform;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */

public class MMShareCodeWebViewActivity extends CommonWebViewActivity {

    @Override
    protected void initViews() {
        super.initViews();
        titleView.setName("我的邀请");
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

    public void setId(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            int id = jsonObject.getInt("id");
            get_party_share_content(id + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sharePartyInfo() {
        super.sharePartyInfo();
        MobclickAgent.onEvent(this,"VIP_share");
    }

    @Override
    public void onCancel(Platform platform, int action) {
        super.onCancel(platform, action);
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        super.onError(platform, action, t);
    }
}
