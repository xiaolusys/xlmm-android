package com.jimei.xiaolumeimei.ui.activity.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/04.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class WebViewActivity extends BaseSwipeBackCompatActivity {

  private Toolbar mToolbar;
  private WebView mWebView;
  private ProgressBar mProgressBar;

  @Override protected void setListener() {
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  @Override protected void initData() {
    mWebView.loadUrl("http://m.xiaolumeimei.com/chaotong.html");
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_actwebview;
  }

  @Override protected void initViews() {
    mProgressBar = (ProgressBar) findViewById(R.id.pb_view);
    mWebView = (WebView) findViewById(R.id.wb_view);
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setTitle("");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.drawable.back);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mWebView.getSettings().setJavaScriptEnabled(true);
    mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    mWebView.getSettings().setLoadWithOverviewMode(true);

    mWebView.setWebChromeClient(new WebChromeClient() {
      @Override public void onProgressChanged(WebView view, int newProgress) {
        mProgressBar.setProgress(newProgress);
        if (newProgress == 100) {
          mProgressBar.setVisibility(View.GONE);
        } else {
          mProgressBar.setVisibility(View.VISIBLE);
        }
      }
    });
    mWebView.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {

    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (mWebView.canGoBack()) {
        mWebView.goBack();
      } else {
        finish();
      }
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

  @Override protected void onPause() {
    super.onPause();
    mWebView.onPause();
  }

  @Override protected void onResume() {
    super.onResume();
    mWebView.onResume();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mWebView.destroy();
  }
}
