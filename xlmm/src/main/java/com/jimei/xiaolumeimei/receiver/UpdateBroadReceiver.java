package com.jimei.xiaolumeimei.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jimei.xiaolumeimei.xlmmService.UpdateService;

/**
 * Created by wisdom on 16/7/26.
 */
public class UpdateBroadReceiver extends BroadcastReceiver {

    public static final String ACTION_RETRY_DOWNLOAD = "action.retry.download";

    @Override
    public void onReceive(Context context, Intent intent) {
        String downloadUrl = intent.getStringExtra(UpdateService.EXTRAS_DOWNLOAD_URL);
        Intent updateIntent = new Intent(context, UpdateService.class);
        updateIntent.putExtra(UpdateService.EXTRAS_DOWNLOAD_URL, downloadUrl);
        context.startService(updateIntent);
    }
}
