package com.jimei.xiaolumeimei.ui.activity.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
  LinearLayout ll_actwebview;
  //private static final String URL =
  //    "http://192.168.1.31:9000/sale/promotion/xlsampleorder/";
  private Toolbar mToolbar;
  private WebView mWebView;
  private ProgressBar mProgressBar;
  private String cookies;
  private String actlink;
  private ActivityBean shareInfo;
  private String domain;

  @Override protected void setListener() {
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        JUtils.Log(TAG, "setNavigationOnClickListener finish");
        finish();
      }
    });
  }

  @Override protected void initData() {
    JUtils.Log(TAG, "initData");
    runOnUiThread(new Runnable() {
      @Override public void run() {
        JUtils.Log(TAG, "initData--" + actlink);
        //syncCookie(WebViewActivity.this, actlink);
        try {
          mWebView.loadUrl(actlink);
        } catch (Exception e) {
          e.printStackTrace();
          JUtils.Log(TAG, "loadUrl--");
        }
      }
    });
  }

  @Override protected void getBundleExtras(Bundle extras) {
    if (extras != null) {
      cookies = extras.getString("cookies");
      domain = extras.getString("domain");
      actlink = extras.getString("actlink");
      domain = extras.getString("domain");
      JUtils.Log(TAG,
          "GET cookie:" + cookies + " actlink:" + actlink + " domain:" + domain);
    }
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_actwebview;
  }

  @SuppressLint("JavascriptInterface") @Override protected void initViews() {
    JUtils.Log(TAG, "initViews");

    //syncCookie(WebViewActivity.this);

    ll_actwebview = (LinearLayout) findViewById(R.id.ll_actwebview);
    mProgressBar = (ProgressBar) findViewById(R.id.pb_view);
    mWebView = (WebView) findViewById(R.id.wb_view);
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setTitle("");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    try {
      if (Build.VERSION.SDK_INT >= 19) {
        mWebView.getSettings().setLoadsImagesAutomatically(true);
      } else {
        mWebView.getSettings().setLoadsImagesAutomatically(false);
      }

      mWebView.getSettings().setJavaScriptEnabled(true);
      mWebView.addJavascriptInterface(new AndroidJsBridge(this), "AndroidBridge");

      //mWebView.getSettings().setAllowFileAccess(true);
      //如果访问的页面中有Javascript，则webview必须设置支持Javascript
      //mWebView.getSettings().setUserAgentString(MyApplication.getUserAgent());
      //mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
      //mWebView.getSettings().setAllowFileAccess(true);
      //mWebView.getSettings().setAppCacheEnabled(true);
      //mWebView.getSettings().setDomStorageEnabled(true);
      //mWebView.getSettings().setDatabaseEnabled(true);
      //mWebView.getSettings().setLoadWithOverviewMode(true);
      //mWebView.getSettings().setUseWideViewPort(true);

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

          //if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
          //  mWebView.getSettings().setLoadsImagesAutomatically(true);
          //}
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

    syncCookie(WebViewActivity.this);
    //syncCookie(WebViewActivity.this, "http://m.xiaolumeimei.com");
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
    CookieSyncManager.getInstance().stopSync();
    mWebView.onPause();
  }

  @Override protected void onResume() {
    super.onResume();
    CookieSyncManager.getInstance().startSync();
    mWebView.onResume();
    ShareSDK.initSDK(this);
  }

  @Override protected void onDestroy() {
    JUtils.Log(TAG, "onDestroy");
    super.onDestroy();
    ll_actwebview.removeView(mWebView);
    mWebView.removeAllViews();
    mWebView.destroy();
  }

  @Override protected void onStop() {
    super.onStop();
    ShareSDK.stopSDK(this);
  }

  //public void syncCookie(Context context, String url) {
  //  try {
  //    CookieSyncManager.createInstance(context);
  //    CookieManager cookieManager = CookieManager.getInstance();
  //    cookieManager.setAcceptCookie(true);
  //    cookieManager.removeSessionCookie();// 移除
  //    cookieManager.removeAllCookie();
  //
  //    cookieManager.setCookie(url, cookies);
  //    CookieSyncManager.getInstance().sync();
  //  } catch (Exception e) {
  //  }
  //}
  public void syncCookie(Context context) {

    try {
      JUtils.Log(TAG, "syncCookie bgn");
      CookieSyncManager.createInstance(context);
      CookieManager cookieManager = CookieManager.getInstance();

      cookieManager.removeSessionCookie();// 移除
      cookieManager.removeAllCookie();

      cookieManager.setAcceptCookie(true);

      JUtils.Log(TAG, domain + "=====" + cookies);
      cookieManager.setCookie(domain, cookies);

      CookieSyncManager.getInstance().sync();
      JUtils.Log(TAG, "syncCookie end");
    } catch (Exception e) {
      e.printStackTrace();
      JUtils.Log(TAG, "syncCookie err:" + e.toString());
    }
  }

  public void getPromotionParams(String uform, String share_link) {

    ActivityModel.getInstance()
        .get_share_content(uform)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ActivityBean>() {
          @Override public void onNext(ActivityBean activityBean) {

            if (null != activityBean) {
              shareInfo = activityBean;
              shareInfo.setLinkQrcode(URL + activityBean.getLinkQrcode());
              JUtils.Log(TAG, "getPromotionParams get_share_content: activeDec="
                  +
                  shareInfo.getActiveDec()
                  + " linkQrcode="
                  + shareInfo.getLinkQrcode()
                  + " "
                  + "title="
                  + shareInfo.getTitle());
              JUtils.Log(TAG, "getPromotionParams get_share_content: uform="
                  + uform
                  + " share_link="
                  + share_link);

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
              } else if (uform.equals("web")) {
                saveTwoDimenCode();
              }
            }
          }
        });
  }

  private void share_wxapp(String myurl, String ufrom) {
    Platform.ShareParams sp = new Platform.ShareParams();

    sp.setTitle(shareInfo.getTitle());
    sp.setText(shareInfo.getActiveDec()
        + " http://m.xiaolumeimei.com/"
        + myurl
        + "&ufrom="
        + ufrom);

    sp.setUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setShareType(Platform.SHARE_WEBPAGE);
    sp.setImageUrl(shareInfo.getShareImg());
    JUtils.Log(TAG, "wxapp: http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);

    Platform wx = ShareSDK.getPlatform(WebViewActivity.this, Wechat.NAME);
    wx.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享
    wx.share(sp);
  }

  private void share_pyq(String myurl, String ufrom) {

    WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
    //sp.setImageUrl(linkQrcode);
    sp.setTitle(shareInfo.getTitle());
    //sp.setText(shareInfo.getActiveDec() + " http://m.xiaolumeimei.com/" + myurl +
    //    "&ufrom=" + ufrom);
    //sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setShareType(Platform.SHARE_WEBPAGE);
    sp.setImageUrl(shareInfo.getShareImg());
    sp.setUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);

    Platform pyq = ShareSDK.getPlatform(WebViewActivity.this, WechatMoments.NAME);
    pyq.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享
    pyq.share(sp);
  }

  private void share_qq(String myurl, String ufrom) {

    get_share_content(ufrom);
    QQ.ShareParams sp = new QQ.ShareParams();
    sp.setTitle(shareInfo.getTitle());

    sp.setText(shareInfo.getActiveDec());
    sp.setImageUrl(shareInfo.getShareImg());

    sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);

    Platform qq = ShareSDK.getPlatform(WebViewActivity.this, QQ.NAME);
    qq.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享
    qq.share(sp);
  }

  private void share_qqspa(String myurl, String ufrom) {
    get_share_content(ufrom);
    QZone.ShareParams sp = new QZone.ShareParams();
    sp.setTitle(shareInfo.getTitle());
    // 标题的超链接
    sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setText(shareInfo.getActiveDec());
    sp.setImageUrl(shareInfo.getShareImg());
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
    sp.setText(shareInfo.getActiveDec()
        + " http://m.xiaolumeimei.com/"
        + myurl
        + "&ufrom="
        + ufrom);
    //sp.setImageUrl(linkQrcode);
    sp.setSiteUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);

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
              shareInfo = activityBean;
              shareInfo.setLinkQrcode(URL + activityBean.getLinkQrcode());

              JUtils.Log(TAG, "get_share_content: desc="
                  + shareInfo.getActiveDec()
                  + " "
                  + "qrcode="
                  + shareInfo.getLinkQrcode()
                  + " title="
                  + shareInfo.getTitle());
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

  public void saveTwoDimenCode() {

    if ((shareInfo == null)
        || (shareInfo.getQrcodeLink() == null)
        || (shareInfo.getQrcodeLink().equals(""))) {
      ActivityModel.getInstance()
          .get_share_content("wxapp")
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<ActivityBean>() {
            @Override public void onNext(ActivityBean activityBean) {

              if (null != activityBean) {

                JUtils.Log(TAG,
                    "saveTowDimenCode : Qrcodelink=" + shareInfo.getQrcodeLink());

                try {
                  WebView webView = new WebView(WebViewActivity.this);
                  webView.setLayoutParams(
                      new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                          Toolbar.LayoutParams.MATCH_PARENT));
                  webView.getSettings().setJavaScriptEnabled(true);

                  webView.getSettings().setAllowFileAccess(true);
                  //如果访问的页面中有Javascript，则webview必须设置支持Javascript
                  //mWebView.getSettings().setUserAgentString(MyApplication.getUserAgent());
                  webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                  webView.getSettings().setAllowFileAccess(true);
                  webView.getSettings().setAppCacheEnabled(true);
                  webView.getSettings().setDomStorageEnabled(true);
                  webView.getSettings().setDatabaseEnabled(true);

                  webView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {

                    }
                  });
                  webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                      view.loadUrl(url);
                      return true;
                    }
                  });
                  webView.loadUrl(shareInfo.getQrcodeLink());
                  View cv = getWindow().getDecorView();
                  Bitmap bmp = catchWebScreenshot(webView, cv.getWidth(), cv.getHeight(),
                      shareInfo.getQrcodeLink(), null);
              /*Bitmap bmp= captureWebView(webView);
              String fileName = Environment.getExternalStorageDirectory()
                  + "/"
                  + Environment.DIRECTORY_DCIM
                  + "/Camera/小鹿美美活动二维码.jpg";
              saveBitmap(bmp, fileName);*/

                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            }
          });
    } else {
      JUtils.Log(TAG, "saveTowDimenCode : Qrcodelink=" + shareInfo.getQrcodeLink());
      try {
        WebView webView = new WebView(WebViewActivity.this);
        webView.setLayoutParams(
            new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT));
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        //mWebView.getSettings().setUserAgentString(MyApplication.getUserAgent());
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
          @Override public void onProgressChanged(WebView view, int newProgress) {

          }
        });
        webView.setWebViewClient(new WebViewClient() {
          @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
          }
        });

        webView.loadUrl(shareInfo.getQrcodeLink());
        //Bitmap bmp= captureWebView(webView);
        View cv = getWindow().getDecorView();
        Bitmap bmp = catchWebScreenshot(webView, cv.getWidth(), cv.getHeight(),
            shareInfo.getQrcodeLink(), null);
        /*String fileName = Environment.getExternalStorageDirectory()
            + "/"
            + Environment.DIRECTORY_DCIM
            + "/Camera/小鹿美美活动二维码.jpg";
        saveBitmap(bmp, fileName);*/

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 截取webView快照(webView加载的整个内容的大小)
   */
  private Bitmap captureWebView(WebView webView) {
    //Picture snapShot = webView.capturePicture();
    View cv = getWindow().getDecorView();
    //Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),snapShot.getHeight(), Bitmap
    //    .Config.ARGB_8888);

    Bitmap bmp =
        Bitmap.createBitmap(cv.getWidth(), cv.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bmp);
    webView.draw(canvas);
    return bmp;
  }

  /**
   * 抓取WEB界面的截屏
   *
   * @param containerWidth 截屏宽度，也就放置WebView的宽度
   * @param containerHeight 截屏高度，也就放置WebView的高度
   * @param baseUrl Base Url
   * @param content 加载的内容
   */
  public Bitmap catchWebScreenshot(final WebView w, final int containerWidth,
      final int containerHeight, final String baseUrl, final String content) {
    final Bitmap b =
        Bitmap.createBitmap(containerWidth, containerHeight, Bitmap.Config.ARGB_8888);
    w.post(new Runnable() {
      public void run() {
        w.setWebViewClient(new WebViewClient() {
          @Override public void onPageFinished(WebView view, String url) {
            JUtils.Log(TAG, "onPageFinished URL=" + url);

            String fileName = Environment.getExternalStorageDirectory()
                + "/"
                + Environment.DIRECTORY_DCIM
                + "/Camera/"
                + R.string.share_2dimen_pic_name
                + ".jpg";
            saveBitmap(b, fileName);
            Toast.makeText(WebViewActivity.this, R.string.share_2dimen_pic_tips,
                Toast.LENGTH_SHORT).show();
          }
        });
        w.setPictureListener(new WebView.PictureListener() {
          public void onNewPicture(WebView view, Picture picture) {
            JUtils.Log(TAG, "onNewPicture ");
            final Canvas c = new Canvas(b);
            view.draw(c);
            //w.setPictureListener(null);

          }
        });
        w.layout(0, 0, containerWidth, containerHeight);
        w.loadUrl(baseUrl);
        //              w.loadDataWithBaseURL(baseUrl, content, "text/html", "UTF-8", null);
      }
    });

    return b;
  }

  /** 保存方法 */
  public void saveBitmap(Bitmap bm, String fileName) {

    File f = new File(fileName);
    if (f.exists()) {
      f.delete();
    }
    try {
      FileOutputStream out = new FileOutputStream(f);
      bm.compress(Bitmap.CompressFormat.PNG, 90, out);
      out.flush();
      out.close();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
