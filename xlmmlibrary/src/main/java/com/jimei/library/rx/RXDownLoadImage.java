package com.jimei.library.rx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import com.jude.utils.JUtils;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/29.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class RXDownLoadImage {

  public static Observable<Uri> saveImageAndGetPathObservable(Context context, String url,
      String title) {
    return Observable.create(new Observable.OnSubscribe<Bitmap>() {
      @Override public void call(Subscriber<? super Bitmap> subscriber) {
        Bitmap bitmap = null;
        try {
          bitmap = Picasso.with(context).load(url).get();
        } catch (IOException e) {
          subscriber.onError(e);
        }
        if (bitmap == null) {
          subscriber.onError(new Exception("无法下载到图片"));
        }
        subscriber.onNext(bitmap);
        subscriber.onCompleted();
      }
    }).flatMap(bitmap -> {
      File appDir =
          new File(Environment.getExternalStorageDirectory(), "xlmm/xiaolumeimei");
      if (!appDir.exists()) {
        appDir.mkdir();
      }
      String fileName = title.replace('/', '-') + ".jpg";
      File file = new File(appDir, fileName);
      try {
        FileOutputStream fos = new FileOutputStream(file);
        assert bitmap != null;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Uri uri = Uri.fromFile(file);
      // 通知图库更新
      Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
      scannerIntent.setData(uri);

      context.sendBroadcast(scannerIntent);

      if (bitmap != null && bitmap.isRecycled()) {
        bitmap.recycle();
      }

      return Observable.just(uri);
    }).subscribeOn(Schedulers.io());
  }

  public static Observable<Uri> saveImageAndGetPathObservableFile(Context context,
      String url, String title) {

    return Observable.create(new Observable.OnSubscribe<File>() {
      @Override public void call(Subscriber<? super File> subscriber) {
        OkHttpUtils.get()
            .url(url)
            .build()
            .execute(new FileCallBack("xlmm", "xiaolumeimei") {
              @Override public void inProgress(float progress) {
                JUtils.Log("NinePic", progress + "");
              }

              @Override public void onError(Call call, Exception e) {

              }

              @Override public void onResponse(File response) {

                if (response == null) {
                  subscriber.onError(new Exception("无法下载到图片"));
                }
                subscriber.onNext(response);
                subscriber.onCompleted();
              }
            });
      }
    }).flatMap(fileResponse -> {
      String fileName = title.replace('/', '-') + ".jpg";
      File file = new File(fileResponse, fileName);
      FileOutputStream fos = null;
      try {
        fos = new FileOutputStream(file);
        fos.flush();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          if (fos != null) fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      Uri uri = Uri.fromFile(file);
      // 通知图库更新
      Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
      scannerIntent.setData(uri);

      context.sendBroadcast(scannerIntent);
      return Observable.just(uri);
    }).subscribeOn(Schedulers.io());
  }

  public static Observable<Uri> saveImageAndGetPathObservableone(Context context,
      List<String> picArry, String title) {

    return Observable.from(picArry).map(s -> s).map(s -> {
      Bitmap bitmap = null;
      try {
        bitmap = Picasso.with(context).load(s).get();
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (bitmap != null) {
        return bitmap;
      }
      return null;
    }).flatMap(bitmap -> {
      File appDir =
          new File(Environment.getExternalStorageDirectory(), "xlmm/xiaolumeimei");
      if (!appDir.exists()) {
        appDir.mkdir();
      }
      String fileName = title.replace('/', '-') + ".jpg";
      File file = new File(appDir, fileName);
      try {
        FileOutputStream fos = new FileOutputStream(file);
        assert bitmap != null;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Uri uri = Uri.fromFile(file);
      // 通知图库更新
      Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
      scannerIntent.setData(uri);

      context.sendBroadcast(scannerIntent);
      return Observable.just(uri);
    }).subscribeOn(Schedulers.io());
  }
}
