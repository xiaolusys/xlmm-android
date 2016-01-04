package com.jimei.xiaolumeimei.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import com.jimei.xiaolumeimei.event.NetWorkEvent;
import com.jimei.xiaolumeimei.rx.RxNetWorkBus;
import com.jimei.xiaolumeimei.utils.NetWorkUtil;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */

public class NetStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(
                ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (NetWorkUtil.isNetWorkConnected(context)) {
                RxNetWorkBus.send(new NetWorkEvent(NetWorkEvent.AVAILABLE));
            } else {
                RxNetWorkBus.send(new NetWorkEvent(NetWorkEvent.UNAVAILABLE));
            }
        }
    }
}
