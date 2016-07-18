package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */

public class MMShareCodeWebViewActivity extends CommonWebViewActivity {

    @Override
    protected void initViews() {
        super.initViews();
        webviewTitle.setText("我的邀请");
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
}
