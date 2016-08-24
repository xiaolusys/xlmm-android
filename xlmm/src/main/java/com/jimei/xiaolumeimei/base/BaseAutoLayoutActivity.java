package com.jimei.xiaolumeimei.base;

import com.jimei.xiaolumeimei.mipush.XiaoMiMessageReceiver;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by wisdom on 16/8/18.
 */
public class BaseAutoLayoutActivity extends AutoLayoutActivity {
    public static XiaoMiMessageReceiver.XiaoMiPushHandler handler = null;

    @Override
    protected void onResume() {
        super.onResume();
        handler = new XiaoMiMessageReceiver.XiaoMiPushHandler(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler = null;
    }

    public static XiaoMiMessageReceiver.XiaoMiPushHandler getHandler() {
        return handler;
    }
}
