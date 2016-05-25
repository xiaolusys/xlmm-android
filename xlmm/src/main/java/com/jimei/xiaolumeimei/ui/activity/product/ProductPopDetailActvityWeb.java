package com.jimei.xiaolumeimei.ui.activity.product;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import com.google.gson.Gson;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ProductSkuDetailsBean;
import com.jimei.xiaolumeimei.htmlJsBridge.modules.AndroidJsBridge;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.widget.FlowLayout;
import com.jimei.xiaolumeimei.widget.TagAdapter;
import com.jimei.xiaolumeimei.widget.TagFlowLayout;
import com.jude.utils.JUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.http.HEAD;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductPopDetailActvityWeb extends BaseSwipeBackCompatActivity {

  private static final String TAG = ProductPopDetailActvityWeb.class.getSimpleName();
  private static final String PONTO_MODULES_PACKAGE =
      "com.jimei.xiaolumeimei.htmlJsBridge.modules";
  public WebView mWebView;
  protected TextView webviewTitle;
  FrameLayout ll_actwebview;
  //private Bitmap bitmap;
  private Toolbar mToolbar;
  private ProgressBar mProgressBar;
  private String cookies;
  private String actlink;
  //private ActivityBean partyShareInfo;
  private String domain;
  private String sessionid;
  private int id;
  private List<ProductSkuDetailsBean.SkuInfoEntity> skuInfo;

  @Override protected void setListener() {
  }

  @Override protected void initData() {
    JUtils.Log(TAG, "initData");
    runOnUiThread(new Runnable() {
      @Override public void run() {
        JUtils.Log(TAG, "initData--" + actlink);

        try {
          Map<String, String> extraHeaders = new HashMap<>();

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
      actlink = extras.getString("actlink");
      id = extras.getInt("id");
      sessionid = extras.getString("Cookie");
      JUtils.Log(TAG,
          "GET cookie:" + cookies + " actlink:" + actlink + " domain:" + domain +
              " sessionid:" + sessionid);
    }
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_popdetail;
  }

  //@TargetApi(Build.VERSION_CODES.KITKAT)
  @SuppressLint("JavascriptInterface") @Override protected void initViews() {
    JUtils.Log(TAG, "initViews");
    ShareSDK.initSDK(this);

    //    Window window = getWindow();
    //    //4.4版本及以上
    //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    //      window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
    //              WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    //    }
    //    //5.0版本及以上
    //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    //      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    //      window.getDecorView()
    //              .setSystemUiVisibility(
    //                      View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    //      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    //      window.setStatusBarColor(Color.TRANSPARENT);
    //    }

    webviewTitle = (TextView) findViewById(R.id.webview_title);
    ll_actwebview = (FrameLayout) findViewById(R.id.ll_actwebview);
    //mProgressBar = (ProgressBar) findViewById(R.id.pb_view);
    mWebView = (WebView) findViewById(R.id.wb_view);
    //    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    //    mToolbar.setTitle("");
    //    setSupportActionBar(mToolbar);
    //    mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    try {
      if (Build.VERSION.SDK_INT >= 19) {
        mWebView.getSettings().setLoadsImagesAutomatically(true);
      } else {
        mWebView.getSettings().setLoadsImagesAutomatically(false);
      }

      String userAgentString = mWebView.getSettings().getUserAgentString();
      mWebView.getSettings().setUserAgentString(userAgentString + "; xlmm;");
      mWebView.getSettings().setJavaScriptEnabled(true);
      mWebView.addJavascriptInterface(new AndroidJsBridge(this), "AndroidBridge");
      //      mPonto = new Ponto(mWebView, PONTO_MODULES_PACKAGE);
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
      mWebView.setDrawingCacheEnabled(true);

      //mWebView.setWebContentsDebuggingEnabled(true);

      showIndeterminateProgressDialog(false);


      mWebView.setWebChromeClient(new WebChromeClient() {
        @Override public void onProgressChanged(WebView view, int newProgress) {

          if (newProgress == 100) {
            hideIndeterminateProgressDialog();
          }
        }
      });

      mWebView.setWebViewClient(new WebViewClient() {

        @Override public void onPageFinished(WebView view, String url) {
          JUtils.Log(TAG, "onPageFinished:" + url);
          CookieSyncManager.getInstance().sync();
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
          //handler.cancel(); 默认的处理方式，WebView变成空白页
          //                        //接受证书
          handler.proceed();
          //handleMessage(Message msg); 其他处理
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
      JUtils.Log(TAG, "set webview err");
    }

    syncCookie(this);
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
    CookieSyncManager.createInstance(this);
    CookieSyncManager.getInstance().stopSync();
    mWebView.onPause();
  }

  @Override protected void onResume() {
    super.onResume();
    CookieSyncManager.createInstance(this);
    CookieSyncManager.getInstance().startSync();
    mWebView.onResume();
    ShareSDK.initSDK(this);
  }

  @Override protected void onDestroy() {
    JUtils.Log(TAG, "onDestroy");
    super.onDestroy();
    ShareSDK.stopSDK(this);
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

      cookieManager.removeSessionCookie();// 移除
      cookieManager.removeAllCookie();

      cookieManager.setAcceptCookie(true);

      cookieManager.setCookie(domain, cookies);

      CookieSyncManager.getInstance().sync();
      JUtils.Log(TAG, "syncCookie end");
    } catch (Exception e) {
      e.printStackTrace();
      JUtils.Log(TAG, "syncCookie err:" + e.toString());
    }
  }

  public void showPop(String json) {
    if (!TextUtils.isEmpty(json)) {
      Gson gson = new Gson();
      ProductSkuDetailsBean productSkuDetailsBean =
          gson.fromJson(json, ProductSkuDetailsBean.class);
      skuInfo = productSkuDetailsBean.getSkuInfo();
      if (null != skuInfo) {
        new MyDialog(this).show();
      }
    }
  }

  public void jumToNativeLogin() {
    Intent intent = new Intent(mContext, LoginActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString("login", "prodcutweb");
    bundle.putString("actlink", actlink);
    intent.putExtras(bundle);
    mContext.startActivity(intent);
    finish();
  }

  public void jumToNatvieCarts() {
    Intent intent = new Intent(mContext, CartActivity.class);
    startActivity(intent);
    finish();
  }

  class MyDialog extends Dialog {
    public MyDialog(Context context) {
      super(context, R.style.MyDialog);
      setDialog();
    }

    private void setDialog() {
      View mView = LayoutInflater.from(getContext()).inflate(R.layout.pop_sku, null);
      TagFlowLayout tagColor = (TagFlowLayout) mView.findViewById(R.id.tag_color);
      TagFlowLayout tagSize = (TagFlowLayout) mView.findViewById(R.id.tag_size);
      TagAdapter<ProductSkuDetailsBean.SkuInfoEntity> tagAdapterColor =
          new TagAdapter<ProductSkuDetailsBean.SkuInfoEntity>(skuInfo) {
            @Override public View getView(FlowLayout parent, int position,
                ProductSkuDetailsBean.SkuInfoEntity skuInfoEntity) {
              TextView tv;
              tv = (TextView) LayoutInflater.from(getContext())
                  .inflate(R.layout.tv, tagColor, false);
              tv.setText(skuInfo.get(position).getName());
              return tv;
            }
          };
      tagColor.setAdapter(tagAdapterColor);
      TagAdapter<ProductSkuDetailsBean.SkuInfoEntity> tagAdapterSize =
          new TagAdapter<ProductSkuDetailsBean.SkuInfoEntity>(skuInfo) {
            @Override public View getView(FlowLayout parent, int position,
                ProductSkuDetailsBean.SkuInfoEntity skuInfoEntity) {
              TextView tv;
              if (!skuInfo.get(position).getSkuItems().get(position).isIsSaleout()) {
                tv = (TextView) LayoutInflater.from(getContext())
                    .inflate(R.layout.tv, tagSize, false);
              } else {
                tv = (TextView) LayoutInflater.from(getOwnerActivity())
                    .inflate(R.layout.tv_issalout, tagSize, false);
              }
              tv.setText(skuInfo.get(position).getSkuItems().get(position).getName());
              return tv;
            }
          };
      tagSize.setAdapter(tagAdapterSize);
      MyDialog.this.setCanceledOnTouchOutside(true);
      Window win = this.getWindow();
      win.setGravity(Gravity.BOTTOM);                       //从下方弹出
      win.getDecorView().setPadding(0, 0, 0, 0);
      WindowManager.LayoutParams lp = win.getAttributes();
      lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //宽度填满
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;  //高度自适应
      win.setAttributes(lp);
      super.setContentView(mView);
    }
  }
}

