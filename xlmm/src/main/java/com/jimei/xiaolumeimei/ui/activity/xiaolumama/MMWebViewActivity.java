package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import cn.sharesdk.framework.ShareSDK;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jude.utils.JUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class MMWebViewActivity extends BaseSwipeBackCompatActivity {
  //private static final String URL =
  //    "http://m.xiaolumeimei.com/sale/promotion/xlsampleorder/";
  private static final String URL = "http://m.xiaolumeimei.com/";
  private static final String TAG = MMWebViewActivity.class.getSimpleName();
  LinearLayout ll_actwebview;
  //private static final String URL =
  //    "http://192.168.1.31:9000/sale/promotion/xlsampleorder/";
  //private Toolbar mToolbar;
  private WebView mWebView;
  private ProgressBar mProgressBar;
  private String cookies;
  private String actlink;
  private String domain;
  private String sessionid;

  @Override protected void setListener() {
    //mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
    //  @Override public void onClick(View v) {
    //    JUtils.Log(TAG, "setNavigationOnClickListener finish");
    //    finish();
    //  }
    //});
  }

  @Override protected void initData() {
    JUtils.Log(TAG, "initData");
    runOnUiThread(new Runnable() {
      @Override public void run() {
        JUtils.Log(TAG, "initData--" + actlink);

        try {
          Map<String, String> extraHeaders = new HashMap<String, String>();

          extraHeaders.put("Cookie", sessionid);

          mWebView.loadUrl(actlink, extraHeaders);
        } catch (Exception e) {
          e.printStackTrace();
          JUtils.Log(TAG, "loadUrl--error");
        }

        JUtils.Log(TAG, "loadUrl--end");
      }
    });
  }

  @Override protected void getBundleExtras(Bundle extras) {
    if (extras != null) {
      cookies = extras.getString("cookies");
      domain = extras.getString("domain");
      actlink = extras.getString("link");

      sessionid = extras.getString("Cookie");
      JUtils.Log(TAG,
          "GET cookie:" + cookies + " actlink:" + actlink + " domain:" + domain +
              " sessionid:" + sessionid);
    }
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mmwebview;
  }

  @SuppressLint("JavascriptInterface") @Override protected void initViews() {
    JUtils.Log(TAG, "initViews");

    ll_actwebview = (LinearLayout) findViewById(R.id.ll_actwebview);
    mProgressBar = (ProgressBar) findViewById(R.id.pb_view);
    mWebView = (WebView) findViewById(R.id.wb_view);
    //mToolbar = (Toolbar) findViewById(R.id.toolbar);
    //mToolbar.setTitle("");
    //setSupportActionBar(mToolbar);
    //mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    try {
      if (Build.VERSION.SDK_INT >= 19) {
        mWebView.getSettings().setLoadsImagesAutomatically(true);
      } else {
        mWebView.getSettings().setLoadsImagesAutomatically(false);
      }

      mWebView.getSettings().setJavaScriptEnabled(true);
      //mWebView.addJavascriptInterface(new AndroidJsBridge(this), "AndroidBridge");

      mWebView.getSettings().setAllowFileAccess(true);
      //如果访问的页面中有Javascript，则webview必须设置支持Javascript
      //mWebView.getSettings().setUserAgentString(MyApplication.getUserAgent());
      mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
      mWebView.getSettings().setAllowFileAccess(true);
      mWebView.getSettings().setAppCacheEnabled(true);
      mWebView.getSettings().setDomStorageEnabled(true);
      mWebView.getSettings().setDatabaseEnabled(true);
      mWebView.getSettings().setLoadWithOverviewMode(true);
      mWebView.getSettings().setUseWideViewPort(true);

      mWebView.setWebChromeClient(new WebChromeClient() {
        @Override public void onProgressChanged(WebView view, int newProgress) {
          JUtils.Log(TAG, "process:" + newProgress);
          mProgressBar.setProgress(newProgress);
          if (newProgress == 100) {
            mProgressBar.setVisibility(View.GONE);
          } else {
            mProgressBar.setVisibility(View.VISIBLE);
          }
        }
      });

      mWebView.setWebViewClient(new WebViewClient() {

        @Override public void onPageFinished(WebView view, String url) {
          JUtils.Log(TAG, "onPageFinished:" + url);

          if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
          }
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler,
            String host, String realm) {
          JUtils.Log(TAG, "onReceivedHttpAuthRequest");
          view.reload();
        }

        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
          JUtils.Log(TAG, "shouldOverrideUrlLoading:" + url);
          view.loadUrl(url);
          return super.shouldOverrideUrlLoading(view, url);
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler,
            SslError error) {
          JUtils.Log(TAG, "onReceivedSslError:");
          handler.proceed();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
      JUtils.Log(TAG, "set webview err");
    }

    syncCookie(MMWebViewActivity.this);
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
        JUtils.Log(TAG, "onKeyDown webview goback");
        mWebView.goBack();
      } else {
        JUtils.Log(TAG, "onKeyDown finish");
        finish();
      }
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

  @Override protected void onPause() {
    super.onPause();
    CookieSyncManager.createInstance(MMWebViewActivity.this);
    CookieSyncManager.getInstance().stopSync();
    mWebView.onPause();
  }

  @Override protected void onResume() {
    super.onResume();
    CookieSyncManager.createInstance(MMWebViewActivity.this);
    CookieSyncManager.getInstance().startSync();
    mWebView.onResume();
    ShareSDK.initSDK(this);
  }

  @Override protected void onDestroy() {
    JUtils.Log(TAG, "onDestroy");
    super.onDestroy();
    if (ll_actwebview != null) {
      ll_actwebview.removeView(mWebView);
    }
    if (mWebView != null) {
      mWebView.removeAllViews();
      mWebView.destroy();
    }
  }

  @Override protected void onStop() {
    super.onStop();
  }

  public void syncCookie(Context context) {

    try {
      JUtils.Log(TAG, "syncCookie bgn");
      CookieSyncManager.createInstance(context);
      CookieManager cookieManager = CookieManager.getInstance();
      cookieManager.removeAllCookie();
      CookieSyncManager.getInstance().sync();
      JUtils.Log(TAG, "syncCookie end");
    } catch (Exception e) {
      e.printStackTrace();
      JUtils.Log(TAG, "syncCookie err:" + e.toString());
    }
  }
}
