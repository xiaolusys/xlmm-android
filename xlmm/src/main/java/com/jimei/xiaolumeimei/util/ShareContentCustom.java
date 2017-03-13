package com.jimei.xiaolumeimei.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

/**
 * Created by wisdom on 17/3/9.
 */

public class ShareContentCustom implements ShareContentCustomizeCallback {

    private Context mContext;

    public ShareContentCustom(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "name");
        map.put(platform.getId() + "", platform.getName());
        MobclickAgent.onEvent(mContext, "ShareID", map);
    }
}