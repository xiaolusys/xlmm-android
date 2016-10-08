package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.MamaFansAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.event.WebViewEvent;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/22.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MamaFansActivity extends BaseSwipeBackCompatActivity {
  private static final String TAG = MamaFansActivity.class.getSimpleName();
  @Bind(R.id.xrv_mmvisitors) XRecyclerView xrvMmvisitors;
  private int page = 2;
  private MamaFansAdapter mAdapter;
  private Subscription subscribe;

  @Override protected void setListener() {
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mmvisitor;
  }

  @Override protected void initViews() {
    initRecyclerView();
  }

  @Override protected void initData() {
    showIndeterminateProgressDialog(false);
    xrvMmvisitors.setVisibility(View.INVISIBLE);
    subscribe = MamaInfoModel.getInstance()
        .getMamaFans("1")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<MamaFansBean>() {
          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
            if (xrvMmvisitors != null) {
              xrvMmvisitors.setVisibility(View.VISIBLE);
            }
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }

          @Override public void onNext(MamaFansBean fansBeen) {
            if (fansBeen != null) {
              if (0 == fansBeen.getCount()) {
                JUtils.Log(TAG, "results.size()=0");
              } else {
                mAdapter.update(fansBeen.getResults());
              }
              if (null == fansBeen.getNext()) {
                xrvMmvisitors.setLoadingMoreEnabled(false);
              }
            }
          }
        });
  }

  private void initRecyclerView() {
    xrvMmvisitors.setLayoutManager(new LinearLayoutManager(this));
    xrvMmvisitors.addItemDecoration(
        new DividerItemDecorationForFooter(this, DividerItemDecoration.VERTICAL_LIST));
    xrvMmvisitors.setRefreshProgressStyle(ProgressStyle.BallPulse);
    xrvMmvisitors.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
    xrvMmvisitors.setArrowImageView(R.drawable.iconfont_downgrey);
    xrvMmvisitors.setPullRefreshEnabled(false);
    xrvMmvisitors.setLoadingMoreEnabled(true);
    mAdapter = new MamaFansAdapter(this);
    xrvMmvisitors.setAdapter(mAdapter);

    xrvMmvisitors.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

      }

      @Override public void onLoadMore() {
        loadMoreData(page + "");
        page++;
      }
    });
  }

  private void loadMoreData(String page) {
    JUtils.Log(TAG, "第" + page);
    Subscription subscribe = MamaInfoModel.getInstance()
        .getMamaFans(page)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<MamaFansBean>() {
          @Override public void onNext(MamaFansBean fansBeen) {
            super.onNext(fansBeen);
            if (fansBeen != null) {
              mAdapter.update(fansBeen.getResults());
              if (null != fansBeen.getNext()) {
              } else {
                Toast.makeText(MamaFansActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                xrvMmvisitors.post(xrvMmvisitors::loadMoreComplete);
                xrvMmvisitors.setLoadingMoreEnabled(false);;
              }
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            xrvMmvisitors.post(xrvMmvisitors::loadMoreComplete);
          }
        });
    addSubscription(subscribe);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onStop() {
    super.onStop();
    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_fans, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_fans:
        SharedPreferences sharedPreferences =
            getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        Intent intent = new Intent(this, FansWebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cookies", sharedPreferences.getString("cookiesString", ""));
        bundle.putString("domain", sharedPreferences.getString("cookiesDomain", ""));
        bundle.putString("Cookie", sharedPreferences.getString("Cookie", ""));
        SharedPreferences sharedPreferences2 =
            XlmmApp.getmContext().getSharedPreferences("APICLIENT", Context.MODE_PRIVATE);
        String baseUrl = "http://" + sharedPreferences2.getString("BASE_URL", "");
        if (baseUrl.equals("http://")) {
          baseUrl = XlmmApi.APP_BASE_URL;
        }
        bundle.putString("actlink", baseUrl + "/pages/fans-explain.html");
        EventBus.getDefault()
            .post(new WebViewEvent(sharedPreferences.getString("cookiesString", ""),
                sharedPreferences.getString("cookiesDomain", ""),
                baseUrl + "/pages/fans-explain.html", -1,
                sharedPreferences.getString("Cookie", "")));
        intent.putExtras(bundle);
        startActivity(intent);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }
}
