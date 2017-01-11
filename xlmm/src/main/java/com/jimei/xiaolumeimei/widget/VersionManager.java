package com.jimei.xiaolumeimei.widget;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;

import java.io.File;

/**
 * Created by wisdom on 16/7/26.
 */

public abstract class VersionManager {

    private OnClickListener mPositiveListener;
    private Dialog dialog;

    public static VersionManager newInstance(int versionCode, String content, boolean msgflag) {
        VersionManager manager = new VersionManager() {

            @Override
            public int getServerVersion() {
                return versionCode;
            }

            @Override
            public String getUpdateContent() {
                return content;
            }

            @Override
            public boolean showMsg() {
                return msgflag;
            }
        };
        return manager;
    }

    public abstract int getServerVersion();

    public abstract String getUpdateContent();

    public abstract boolean showMsg();

    public void checkVersion(final Context context) {
        int localVersion = JUtils.getAppVersionCode();
        if (getServerVersion() > localVersion) {
            if (!isWifi(context)) {
                if (showMsg()) {
                    JUtils.Toast("版本有更新哦,请连接WiFi更新!");
                }
            }
            View view = LayoutInflater.from(context).inflate(R.layout.update_dialog, null);

            dialog = new Dialog(context, R.style.CustomDialog);
            dialog.setContentView(view);
            dialog.setCancelable(true);

            Button ignoreBtn = (Button) view.findViewById(R.id.ignore);
            Button okBtn = (Button) view.findViewById(R.id.ok);
            TextView contentTv = (TextView) view.findViewById(R.id.content);

            okBtn.setOnClickListener(mPositiveListener);
            contentTv.setText(getUpdateContent());
            ignoreBtn.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        } else {
            if (showMsg()) {
                JUtils.Toast("当前已是最新版本!");
            }
            File updateFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()
                    + File.separator
                    + context.getResources().getString(R.string.app_name)
                    + ".apk");
            if (updateFile.exists()) {
                updateFile.delete();
            }
        }
    }

    public void setPositiveListener(OnClickListener positiveListener) {
        mPositiveListener = positiveListener;
    }

    private boolean isWifi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        return netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public Dialog getDialog() {
        return dialog;
    }
}