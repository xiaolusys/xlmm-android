package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.schedulers.Schedulers;

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
  private String title, sharelink, desc, shareimg;
  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Override protected void setListener() {
    //mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
    //  @Override public void onClick(View v) {
    //    JUtils.Log(TAG, "setNavigationOnClickListener finish");
    //    finish();
    //  }
    //});
  }

  @Override protected void initData() {

    MMProductModel.getInstance()
            .getShareShopping()
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<MMShoppingBean>() {

              @Override public void onNext(MMShoppingBean mmShoppingBean) {

                if (null != mmShoppingBean) {
                  title = (String) mmShoppingBean.getShopInfo().getName();
                  sharelink = mmShoppingBean.getShopInfo().getShopLink();
                  shareimg = mmShoppingBean.getShopInfo().getThumbnail();
                  desc = mmShoppingBean.getShopInfo().getDesc();
                }
              }
            });

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
    StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent),0);
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

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

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_store, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {


    if (item.getItemId() == R.id.action_share) {
      share_shopping(title, sharelink, desc, shareimg);
    }

    return super.onOptionsItemSelected(item);
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

  private void share_shopping(String title, String sharelink, String desc,
                              String shareimg) {
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    oks.setTitle(title);
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    oks.setTitleUrl(sharelink);
    // text是分享文本，所有平台都需要这个字段
    oks.setText(desc);
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    //oks.setImagePath(filePara.getFilePath());//确保SDcard下面存在此张图片
    //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
    oks.setImageUrl(shareimg);
    oks.setUrl(sharelink);

    // 启动分享GUI
    oks.show(this);
  }
}
