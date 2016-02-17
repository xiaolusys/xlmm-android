package com.jimei.xiaolumeimei.ui.activity.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.htmlJsBridge.AndroidJsBridge;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/04.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class WebViewActivity extends BaseSwipeBackCompatActivity {

  //private static final String URL =
  //    "http://m.xiaolumeimei.com/sale/promotion/xlsampleorder/";

  private static final String URL =
      "http://192.168.1.31:9000/sale/promotion/xlsampleorder/";
  private Toolbar mToolbar;
  private WebView mWebView;
  private ProgressBar mProgressBar;
  private SharedPreferences sharedPreferences;

  @Override protected void setListener() {
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  @Override protected void initData() {
    mWebView.loadUrl(URL);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_actwebview;
  }

  @SuppressLint("JavascriptInterface") @Override protected void initViews() {
    mProgressBar = (ProgressBar) findViewById(R.id.pb_view);
    mWebView = (WebView) findViewById(R.id.wb_view);
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setTitle("");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mWebView.getSettings().setJavaScriptEnabled(true);
    mWebView.addJavascriptInterface(new AndroidJsBridge(this), "AndroidBridge");

    mWebView.getSettings().setAllowFileAccess(true);
    //如果访问的页面中有Javascript，则webview必须设置支持Javascript
    //mWebView.getSettings().setUserAgentString(MyApplication.getUserAgent());
    mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    mWebView.getSettings().setAllowFileAccess(true);
    mWebView.getSettings().setAppCacheEnabled(true);
    mWebView.getSettings().setDomStorageEnabled(true);
    mWebView.getSettings().setDatabaseEnabled(true);

    syncCookie(getApplicationContext(), URL);
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

  public void syncCookie(Context context, String url) {
    try {
      //CookieSyncManager.createInstance(context);
      //CookieManager cookieManager = CookieManager.getInstance();
      //cookieManager.setAcceptCookie(true);
      //cookieManager.removeSessionCookie();// 移除
      //cookieManager.removeAllCookie();
      //String oldCookie = cookieManager.getCookie(url);

      //URL aURL = new URL(url);

      //StringBuilder sbCookie = new StringBuilder();
      //sbCookie.append(String.format("sessionid" + "=%s", XlmmApp.cookies));
      ////webview在使用cookie前会前判断保存cookie的domain和当前要请求的domain是否相同，相同才会发送cookie
      ////sbCookie.append(
      ////    String.format(";domain=%s", aURL.getHost())); //注意，是getHost()，不是getAuthority(),
      //sbCookie.append(String.format(";path=%s", "/"));
      //
      //String cookieValue = sbCookie.toString();
      //cookieManager.setCookie(url, XlmmApp.cookies);
      //CookieSyncManager.getInstance().sync();

      sharedPreferences =
          context.getSharedPreferences("COOKIESxlmm", Context.MODE_PRIVATE);

      String cookies = sharedPreferences.getString("Cookies", "");

      CookieSyncManager.createInstance(context);
      CookieManager cookieManager = CookieManager.getInstance();
      cookieManager.setAcceptCookie(true);
      cookieManager.removeSessionCookie();// 移除
      cookieManager.removeAllCookie();
      cookieManager.setCookie(url, cookies);//cookies是在HttpClient中获得的cookie
      CookieSyncManager.getInstance().sync();
    } catch (Exception e) {
    }
  }

  public void getPromotionParams(String uform, String share_link) {

    if (uform.equals("wxapp")) {
      share_wxapp(share_link);
    } else if (uform.equals("pyq")) {
      share_pyq(share_link);
    } else if (uform.equals("qq")) {
      share_qq(share_link);
    } else if (uform.equals("qqspa")) {
      share_qqspa(share_link);
    } else if (uform.equals("sinawb")) {
      share_sina(share_link);
    }
  }

  private void share_wxapp(String myurl) {

    Wechat.ShareParams sp = new Wechat.ShareParams();
    sp.setText("小鹿美美");
    sp.setUrl("http://m.xiaolumeimei.com/" + myurl);

    Platform wxapp = ShareSDK.getPlatform(Wechat.NAME);
    //weibo.setPlatformActionListener(paListener); // 设置分享事件回调
    // 执行图文分享
    wxapp.share(sp);
  }

  private void share_pyq(String myurl) {

    WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
    sp.setText("小鹿美美");
    sp.setUrl("http://m.xiaolumeimei.com/" + myurl);

    Platform pyq = ShareSDK.getPlatform(WechatMoments.NAME);
    //weibo.setPlatformActionListener(paListener); // 设置分享事件回调
    // 执行图文分享
    pyq.share(sp);
  }

  private void share_qq(String myurl) {

    QQ.ShareParams sp = new QQ.ShareParams();
    sp.setText("小鹿美美");
    sp.setUrl("http://m.xiaolumeimei.com/" + myurl);

    Platform qq = ShareSDK.getPlatform(QQ.NAME);
    //weibo.setPlatformActionListener(paListener); // 设置分享事件回调
    // 执行图文分享
    qq.share(sp);
  }

  private void share_qqspa(String myurl) {

    QZone.ShareParams sp = new QZone.ShareParams();
    sp.setText("小鹿美美");
    sp.setUrl("http://m.xiaolumeimei.com/" + myurl);

    Platform qspa = ShareSDK.getPlatform(QZone.NAME);
    //weibo.setPlatformActionListener(paListener); // 设置分享事件回调
    // 执行图文分享
    qspa.share(sp);
  }

  private void share_2dimencode(String myurl) {

    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
    sp.setText("小鹿美美");
    sp.setUrl("http://m.xiaolumeimei.com/" + myurl);

    Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
    //weibo.setPlatformActionListener(paListener); // 设置分享事件回调
    // 执行图文分享
    weibo.share(sp);
  }

  private void share_sina(String myurl) {

    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
    sp.setText("小鹿美美");
    sp.setUrl("http://m.xiaolumeimei.com/" + myurl);

    Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
    //weibo.setPlatformActionListener(paListener); // 设置分享事件回调
    // 执行图文分享
    weibo.share(sp);
  }
}
