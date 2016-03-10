package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import butterknife.Bind;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.library.rx.RXDownLoadImage;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.NinePicAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.utils.AppUtils;
import com.jimei.xiaolumeimei.widget.timecircleview.TickCircleProgress;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/29.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMNinePicActivity extends BaseSwipeBackCompatActivity
    implements SwipeRefreshLayout.OnRefreshListener,
    NinePicAdapter.setOnclickSaveListener {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.circleLv) ListView circleLv;
  @Bind(R.id.mRefreshLayout) SwipeRefreshLayout mRefreshLayout;
  @Bind(R.id.myTickCircleProgress) TickCircleProgress myTickCircleProgress;

  private NinePicAdapter mAdapter;

  @Override protected void setListener() {
    mRefreshLayout.setOnRefreshListener(this);
    mAdapter.setOnclickSaveListener(this);
  }

  @Override protected void initData() {
    loadData();
  }

  private void loadData() {

    if (calcLeftTime() > 0) {
      myTickCircleProgress.setVisibility(View.VISIBLE);
    } else {
      myTickCircleProgress.setVisibility(View.GONE);
      showIndeterminateProgressDialog(false);
      MMProductModel.getInstance()
          .getNinePic()
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<List<NinePicBean>>() {

            @Override public void onCompleted() {
              super.onCompleted();
              hideIndeterminateProgressDialog();
            }

            @Override public void onError(Throwable e) {
              super.onError(e);
              e.printStackTrace();
            }

            @Override public void onNext(List<NinePicBean> ninePicBean) {
              if (ninePicBean != null) {
                mAdapter.setDatas(ninePicBean);
                mAdapter.notifyDataSetChanged();
              } else {
                myTickCircleProgress.setVisibility(View.VISIBLE);
              }
            }
          });
    }
  }

  private void loadDataRefresh() {

    showIndeterminateProgressDialog(false);
    MMProductModel.getInstance()
        .getNinePic()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<NinePicBean>>() {

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }

          @Override public void onNext(List<NinePicBean> ninePicBean) {
            if (ninePicBean != null) {
              mAdapter.setDatas(ninePicBean);
              mAdapter.notifyDataSetChanged();
            } else {
              myTickCircleProgress.setVisibility(View.VISIBLE);
            }
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_ninepic;
  }

  @Override protected void initViews() {

    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light, android.R.color.holo_orange_light,
        android.R.color.holo_red_light);

    mAdapter = new NinePicAdapter(this);
    circleLv.setAdapter(mAdapter);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onRefresh() {
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        loadDataRefresh();
        mRefreshLayout.setRefreshing(false);
      }
    }, 2000);
  }

  private long calcLeftTime() {

    Date now = new Date();
    Date nextDay14PM = new Date();
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(nextDay14PM);
    calendar.set(Calendar.HOUR_OF_DAY, 10);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    nextDay14PM = calendar.getTime();

    return nextDay14PM.getTime() - now.getTime();
  }

  private void saveImageToGallery(String url, String mImageTitle) {

    Subscription subscribe =
        RXDownLoadImage.saveImageAndGetPathObservable(this, url, mImageTitle)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(uri -> {
              //File appDir = new File(Environment.getExternalStorageDirectory(), "XlMMImage");
              //String msg = String.format(("图片已经保存到"),
              //        appDir.getAbsolutePath());
              //JUtils.Toast(msg);
            }, error -> JUtils.Toast(error.getMessage() + "\n再试试..."));
    addSubscription(subscribe);

    //Observable<Object> observable =
    //    Observable.from(picArry).flatMap(new Func1<String, Observable<Bitmap>>() {
    //      @Override public Observable<Bitmap> call(String s) {
    //        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
    //          @Override public void call(Subscriber<? super Bitmap> subscriber) {
    //            Bitmap bitmap = null;
    //            try {
    //              bitmap = Picasso.with(MMNinePicActivity.this).load(s).get();
    //            } catch (IOException e) {
    //              subscriber.onError(e);
    //            }
    //            if (bitmap == null) {
    //              subscriber.onError(new Exception("无法下载到图片"));
    //            }
    //            subscriber.onNext(bitmap);
    //            subscriber.onCompleted();
    //          }
    //        });
    //      }
    //    }).flatMap(new Func1<Bitmap, Observable<?>>() {
    //      @Override public Observable<?> call(Bitmap bitmap) {
    //        File appDir =
    //            new File(Environment.getExternalStorageDirectory(), "xlmm/xiaolumeimei");
    //        if (!appDir.exists()) {
    //          appDir.mkdir();
    //        }
    //        String fileName = mImageTitle.replace('/', '-') + ".jpg";
    //        File file = new File(appDir, fileName);
    //        try {
    //          FileOutputStream fos = new FileOutputStream(file);
    //          assert bitmap != null;
    //          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
    //          fos.flush();
    //          fos.close();
    //        } catch (IOException e) {
    //          e.printStackTrace();
    //        }
    //
    //        Uri uri = Uri.fromFile(file);
    //        // 通知图库更新
    //        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
    //        scannerIntent.setData(uri);
    //
    //        sendBroadcast(scannerIntent);
    //        return Observable.just(uri);
    //      }
    //    }).subscribeOn(Schedulers.io());
    //
    //Subscription subscribe = observable.subscribe();

    //Observable<Uri> observable = Observable.from(picArry).map(s -> s).map(s -> {
    //  Bitmap bitmap = null;
    //  try {
    //    bitmap = Picasso.with(MMNinePicActivity.this).load(s).get();
    //  } catch (IOException e) {
    //    e.printStackTrace();
    //  }
    //  if (bitmap != null) {
    //    return bitmap;
    //  }
    //  return null;
    //}).flatMap(bitmap -> {
    //  File appDir =
    //      new File(Environment.getExternalStorageDirectory(), "xlmm/xiaolumeimei");
    //  if (!appDir.exists()) {
    //    appDir.mkdir();
    //  }
    //  String fileName = mImageTitle.replace('/', '-') + ".jpg";
    //  File file = new File(appDir, fileName);
    //  try {
    //    FileOutputStream fos = new FileOutputStream(file);
    //    assert bitmap != null;
    //    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
    //    fos.flush();
    //    fos.close();
    //  } catch (IOException e) {
    //    e.printStackTrace();
    //  }
    //
    //  Uri uri = Uri.fromFile(file);
    //  // 通知图库更新
    //  Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
    //  scannerIntent.setData(uri);
    //
    //  sendBroadcast(scannerIntent);
    //  return Observable.just(uri);
    //}).subscribeOn(Schedulers.io());

    //addSubscription(subscribe);
  }

  @Override public void save(List<String> picArry, int position) {

    if (!AppUtils.isFastDoubleClick()) {
      if (picArry != null) {
        for (int i = 0; i < picArry.size(); i++) {
          saveImageToGallery(
              picArry.get(i) + "?imageMogr2/thumbnail/578/format/jpg/quality/90",
              picArry.get(i));
        }
        new MaterialDialog.Builder(this).
            content("图片已经保存，前往分享吧").
            positiveText("OK").
            callback(new MaterialDialog.ButtonCallback() {
              @Override public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                dialog.dismiss();
              }
            }).show();
      }
    }

    //saveImageToGallery(picArry, picArry.get(position));
    //new MaterialDialog.Builder(this).
    //    content("图片已经保存，前往分享吧").
    //    positiveText("OK").
    //    callback(new MaterialDialog.ButtonCallback() {
    //      @Override public void onPositive(MaterialDialog dialog) {
    //        super.onPositive(dialog);
    //        dialog.dismiss();
    //      }
    //    }).show();
  }
}
