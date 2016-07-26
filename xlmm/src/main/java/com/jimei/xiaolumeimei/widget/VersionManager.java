package com.jimei.xiaolumeimei.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmConst;

import java.io.File;

/**
 * Created by wisdom on 16/7/26.
 */

public abstract class VersionManager {

    private DialogInterface.OnClickListener mPositiveListener;
    private DialogInterface.OnClickListener mNegativeListener;

    public abstract int getServerVersion();

    public void checkVersion(final Context context) {
        if (!isWifi(context)) {
            return;
        }
        int localVersion = XlmmConst.getVersionCode(context);
        if (getServerVersion() > localVersion) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("发现新版本，是否更新");
            builder.setCancelable(false);
            builder.setPositiveButton("现在更新", mPositiveListener);
            builder.setNegativeButton("下次更新", mNegativeListener);
            builder.create().show();
        } else {
            File updateFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()
                    + File.separator
                    + context.getResources().getString(R.string.app_name)
                    + ".apk");
            if (updateFile.exists()) {
                updateFile.delete();
            }

        }
    }

    public void setPositiveListener(DialogInterface.OnClickListener positiveListener) {
        mPositiveListener = positiveListener;
    }

    public void setNegativeListener(DialogInterface.OnClickListener negativeListener) {
        this.mNegativeListener = negativeListener;
    }


    private boolean isWifi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        return netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

}