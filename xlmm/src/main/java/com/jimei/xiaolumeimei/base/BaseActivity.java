package com.jimei.xiaolumeimei.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p/>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public abstract class BaseActivity extends AutoLayoutActivity{

  private CompositeSubscription mCompositeSubscription;

  abstract protected int provideContentViewId();

  public CompositeSubscription getCompositeSubscription() {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }

    return this.mCompositeSubscription;
  }

  public void addSubscription(Subscription s) {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }

    this.mCompositeSubscription.add(s);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(provideContentViewId());
//    StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent),0);
    ButterKnife.bind(this);

    initView();
    initData();

    setListener();
  }

  protected abstract void setListener();//设置监听

  protected abstract void initData();//初始化数据

  protected abstract void initView();//初始化view

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  @Override protected void onResume() {
    super.onResume();
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPause(this);
  }

  public void finishBack(Toolbar toolbar) {
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  @Override protected void onStop() {
    super.onStop();
    if (this.mCompositeSubscription != null) {
      this.mCompositeSubscription.unsubscribe();
    }
  }
}
