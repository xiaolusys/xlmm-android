package com.jimei.xiaolumeimei.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.receiver.UpdateBroadReceiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by wisdom on 16/7/26.
 */
public class UpdateService extends Service {

    public static final String EXTRAS_DOWNLOAD_URL = "extras_download_url";
    private static final int DOWNLOAD_COMPLETE = 0;
    private static final int DOWNLOAD_FAIL = 1;
    private static final int FLAG_UPDATE = 0;
    private static final int PROGRESS_MAX = 100;
    private static final int PROGRESS_MIN = 0;
    private static final int STEP_LENGTH = 3;

    private File mUpdateFile;
    private File mUpdateDir;

    private String mDownloadUrl;

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    private UpdateHandler mHandler = new UpdateHandler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            mUpdateDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            mUpdateFile = new File(mUpdateDir, getResources().getString(R.string.app_name) + ".apk");
        }
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        mBuilder.setContentTitle(getResources().getString(R.string.app_name));
        mBuilder.setProgress(PROGRESS_MAX, PROGRESS_MIN, false);
        mBuilder.setTicker("正在下载" + getResources().getString(R.string.app_name));
        mBuilder.setOngoing(true);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(FLAG_UPDATE, mBuilder.build());
        mDownloadUrl = intent.getStringExtra(EXTRAS_DOWNLOAD_URL);
        new Thread(new DownloadRunnable()).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class UpdateHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    Intent installIntent = new Intent();
                    installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    installIntent.setAction(android.content.Intent.ACTION_VIEW);
                    installIntent.setDataAndType(Uri.fromFile(mUpdateFile), "application/vnd.android.package-archive");
                    mBuilder.setContentIntent(PendingIntent.getActivity(UpdateService.this, 0, installIntent,
                        PendingIntent.FLAG_ONE_SHOT));
                    mBuilder.setAutoCancel(true);
                    mBuilder.setOngoing(false);
                    mNotificationManager.notify(FLAG_UPDATE, mBuilder.build());
                    startActivity(installIntent);
                    stopSelf();
                    break;
                case DOWNLOAD_FAIL:
                    Intent intentClick = new Intent();
                    intentClick.putExtra(EXTRAS_DOWNLOAD_URL, mDownloadUrl);
                    intentClick.setAction(UpdateBroadReceiver.ACTION_RETRY_DOWNLOAD);
                    mBuilder.setOngoing(false);
                    mBuilder.setContentText("下载失败，请点击重试");
                    mBuilder.setTicker(getResources().getString(R.string.app_name) + "下载失败，请点击重试");
                    mBuilder.setContentIntent(PendingIntent.getBroadcast(UpdateService.this, 0, intentClick,
                        PendingIntent.FLAG_ONE_SHOT));
                    mNotificationManager.notify(FLAG_UPDATE, mBuilder.build());
                    stopSelf();
                    break;
                default:
                    stopSelf();
                    break;
            }
        }
    }

    private class DownloadRunnable implements Runnable {
        Message mMessage = mHandler.obtainMessage();

        @Override
        public void run() {
            try {
                if (!mUpdateDir.exists()) {
                    mUpdateDir.mkdirs();
                }
                if (!mUpdateFile.exists()) {
                    mUpdateFile.createNewFile();
                }
                downloadFile();
                mMessage.what = DOWNLOAD_COMPLETE;
                mHandler.sendMessage(mMessage);
            } catch (IOException e) {
                e.printStackTrace();
                mMessage.what = DOWNLOAD_FAIL;
                mHandler.sendMessage(mMessage);
            }
        }

        public void downloadFile() throws IOException {
            HttpURLConnection connection = null;
            InputStream is = null;
            FileOutputStream fos = null;
            int totalSize;
            double totalSizeOfMB;
            int currentSize = 0;
            int stepCount = 0;
            try {
                URL url = new URL(mDownloadUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(6000);
                connection.setReadTimeout(6000);
                totalSize = connection.getContentLength();
                totalSizeOfMB = totalSize / 1000000.0;

                if (connection.getResponseCode() == 200) {
                    DecimalFormat format = new DecimalFormat("0.00");

                    is = connection.getInputStream();
                    fos = new FileOutputStream(mUpdateFile, false);

                    byte buffer[] = new byte[4096];
                    int readSize;

                    while ((readSize = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, readSize);
                        currentSize += readSize;
                        if (stepCount == 0 || currentSize * PROGRESS_MAX / totalSize - STEP_LENGTH >= stepCount) {
                            mBuilder.setProgress(PROGRESS_MAX, currentSize * PROGRESS_MAX / totalSize, false);
                            mBuilder.setContentInfo(format.format(currentSize / 1000000.0) + "M/" + format.format
                                (totalSizeOfMB) + "M");
                            mBuilder.setContentText("正在下载");
                            mNotificationManager.notify(FLAG_UPDATE, mBuilder.build());
                            stepCount += STEP_LENGTH;
                        }
                    }
                    mBuilder.setContentText("下载完成");
                    mBuilder.setTicker(getResources().getString(R.string.app_name) + "下载完成");
                    mBuilder.setProgress(PROGRESS_MAX, PROGRESS_MAX, false);
                    mBuilder.setContentInfo(format.format(totalSizeOfMB) + "M");
                    mNotificationManager.notify(FLAG_UPDATE, mBuilder.build());
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

}