package com.jimei.xiaolumeimei.ui.activity.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.htmlJsBridge.AndroidJsBridge;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.mob.tools.utils.UIHandler;
import java.util.HashMap;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/04.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class WebViewActivity extends BaseSwipeBackCompatActivity
    implements PlatformActionListener, Handler.Callback {

  private static final int MSG_TOAST = 1;
  private static final int MSG_ACTION_CCALLBACK = 2;
  private static final int MSG_CANCEL_NOTIFY = 3;

  //private static final String URL =
  //    "http://m.xiaolumeimei.com/sale/promotion/xlsampleorder/";
  private static final String URL = "http://m.xiaolumeimei.com/";
  private static final String TAG = WebViewActivity.class.getSimpleName();
  //private static final String URL =
  //    "http://192.168.1.31:9000/sale/promotion/xlsampleorder/";
  private Toolbar mToolbar;
  private WebView mWebView;
  private ProgressBar mProgressBar;
  private String cookies;
  private String actlink;
  private String activeDec;
  private String linkQrcode;
  private String title;

  @Override protected void setListener() {
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  @Override protected void initData() {
    mWebView.loadUrl(actlink);
  }

  @Override protected void getBundleExtras(Bundle extras) {

    cookies = extras.getString("cookies");
    actlink = extras.getString("actlink");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_actwebview;
  }

  @SuppressLint("JavascriptInterface") @Override protected void initViews() {

    syncCookie(WebViewActivity.this, actlink);

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
    ShareSDK.initSDK(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mWebView.destroy();

  }

  @Override protected void onStop() {
    super.onStop();
    ShareSDK.stopSDK(this);

  }

  public void syncCookie(Context context, String url) {
    try {
      CookieSyncManager.createInstance(context);
      CookieManager cookieManager = CookieManager.getInstance();
      cookieManager.setAcceptCookie(true);
      cookieManager.removeSessionCookie();// 移除
      cookieManager.removeAllCookie();

      cookieManager.setCookie(url, cookies);
      CookieSyncManager.getInstance().sync();
    } catch (Exception e) {
    }
  }


  public void getPromotionParams(String uform, String share_link) {

    if (uform.equals("wxapp")) {
      share_wxapp(share_link, uform);
    } else if (uform.equals("pyq")) {
      share_pyq(share_link, uform);
    } else if (uform.equals("qq")) {

      share_qq(share_link, uform);
    } else if (uform.equals("qqspa")) {
      share_qqspa(share_link, uform);
    } else if (uform.equals("sinawb")) {
      share_sina(share_link, uform);
    }
  }

  private void share_wxapp(String myurl, String ufrom) {
    Platform.ShareParams sp = new Platform.ShareParams();
    //sp.setImageUrl(linkQrcode);
    sp.setTitle(title);
    sp.setText(activeDec);

    sp.setUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setShareType(Platform.SHARE_WEBPAGE);

    Platform wx = ShareSDK.getPlatform(WebViewActivity.this, Wechat.NAME);
    wx.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享
    wx.share(sp);
  }

  private void share_pyq(String myurl, String ufrom) {

    WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
    //sp.setImageUrl(linkQrcode);
    sp.setTitle(title);
    sp.setText(activeDec);
    sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setShareType(Platform.SHARE_WEBPAGE);
    Platform pyq = ShareSDK.getPlatform(WebViewActivity.this, WechatMoments.NAME);
    pyq.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享
    pyq.share(sp);
  }

  private void share_qq(String myurl, String ufrom) {

    get_share_content(ufrom);
    QQ.ShareParams sp = new QQ.ShareParams();
    sp.setTitle(title);

    sp.setText(activeDec);
    sp.setImageUrl(linkQrcode);

    sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);

    Platform qq = ShareSDK.getPlatform(WebViewActivity.this, QQ.NAME);
    qq.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享
    qq.share(sp);
  }

  private void share_qqspa(String myurl, String ufrom) {
    get_share_content(ufrom);
    QZone.ShareParams sp = new QZone.ShareParams();
    sp.setTitle(title);
    // 标题的超链接
    sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setText(activeDec);
    sp.setImageUrl(linkQrcode);
    //sp.setSite("发布分享的网站名称");
    sp.setSiteUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);

    Platform qzone = ShareSDK.getPlatform(WebViewActivity.this, QZone.NAME);
    qzone.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享
    qzone.share(sp);
  }

  private void share_2dimencode(String myurl, String ufrom) {

    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
    sp.setText("小鹿美美");
    sp.setUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);

    Platform weibo = ShareSDK.getPlatform(WebViewActivity.this, SinaWeibo.NAME);
    //weibo.setPlatformActionListener(paListener); // 设置分享事件回调
    // 执行图文分享
    weibo.share(sp);
  }

  private void share_sina(String myurl, String ufrom) {
    get_share_content(ufrom);
    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
    //sp.setTitle(title);
    //sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setText(activeDec);
    sp.setImageUrl(linkQrcode);
    //sp.setUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);

    Platform weibo = ShareSDK.getPlatform(WebViewActivity.this, SinaWeibo.NAME);
    weibo.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享
    weibo.share(sp);
  }

  public void get_share_content(String ufrom) {
    ActivityModel.getInstance()
        .get_share_content(ufrom)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ActivityBean>() {
          @Override public void onNext(ActivityBean activityBean) {

            if (null != activityBean) {
              activeDec = activityBean.getActiveDec();
              linkQrcode = URL + activityBean.getLinkQrcode();
              title = activityBean.getTitle();
            }
          }
        });
  }

  @Override public void onCancel(Platform platform, int action) {
    // 取消
    Message msg = new Message();
    msg.what = MSG_ACTION_CCALLBACK;
    msg.arg1 = 3;
    msg.arg2 = action;
    msg.obj = platform;
    UIHandler.sendMessage(msg, this);
  }

  @Override
  public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
    // 成功
    Message msg = new Message();
    msg.what = MSG_ACTION_CCALLBACK;
    msg.arg1 = 1;
    msg.arg2 = action;
    msg.obj = platform;
    UIHandler.sendMessage(msg, this);
  }

  @Override public void onError(Platform platform, int action, Throwable t) {
    // 失敗
    //打印错误信息,print the error msg
    t.printStackTrace();
    //错误监听,handle the error msg
    Message msg = new Message();
    msg.what = MSG_ACTION_CCALLBACK;
    msg.arg1 = 2;
    msg.arg2 = action;
    msg.obj = t;
    UIHandler.sendMessage(msg, this);
  }

  public boolean handleMessage(Message msg) {
    switch (msg.arg1) {
      case 1: {
        // 成功
        Toast.makeText(WebViewActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
        JUtils.Log(TAG, "分享回调成功------------");
      }
      break;
      case 2: {
        // 失败
        Toast.makeText(WebViewActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
      }
      break;
      case 3: {
        // 取消
        Toast.makeText(WebViewActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
      }
      break;
    }

    return false;
  }
}
