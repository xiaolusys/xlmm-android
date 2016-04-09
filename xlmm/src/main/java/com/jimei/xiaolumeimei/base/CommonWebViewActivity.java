package com.jimei.xiaolumeimei.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.htmlJsBridge.AndroidJsBridge;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.utils.BitmapUtil;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.mob.tools.utils.UIHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/04.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CommonWebViewActivity extends BaseSwipeBackCompatActivity
    implements PlatformActionListener, Handler.Callback {

  private static final int MSG_ACTION_CCALLBACK = 2;

  private static final String TAG = CommonWebViewActivity.class.getSimpleName();
  LinearLayout ll_actwebview;
  private Toolbar mToolbar;
  private WebView mWebView;
  private ProgressBar mProgressBar;
  private String cookies;
  private String actlink;
  private ActivityBean partyShareInfo;
  private String domain;
  private String sessionid;
  private int id;
  protected TextView webviewTitle;

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

    get_party_share_content(id + "");
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
    return R.layout.activity_actwebview;
  }

//  @TargetApi(Build.VERSION_CODES.KITKAT)
  @SuppressLint("JavascriptInterface") @Override protected void initViews() {
    JUtils.Log(TAG, "initViews");
    ShareSDK.initSDK(this);

    webviewTitle = (TextView) findViewById(R.id.webview_title);
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
//      mWebView.setWebContentsDebuggingEnabled(true);

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

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_share:
        JUtils.Log(TAG, "party share");
        sharePartyInfo();
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_shareproduct, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override protected void onPause() {
    super.onPause();
    CookieSyncManager.createInstance(CommonWebViewActivity.this);
    CookieSyncManager.getInstance().stopSync();
    mWebView.onPause();
  }

  @Override protected void onResume() {
    super.onResume();
    CookieSyncManager.createInstance(CommonWebViewActivity.this);
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
    ShareSDK.stopSDK(this);
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
        Toast.makeText(CommonWebViewActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
        JUtils.Log(TAG, "分享回调成功------------");
      }
      break;
      case 2: {
        // 失败
        Toast.makeText(CommonWebViewActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
      }
      break;
      case 3: {
        // 取消
        Toast.makeText(CommonWebViewActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
      }
      break;
    }

    return false;
  }

  public void saveTwoDimenCode() {

    if ((partyShareInfo == null)
        || (partyShareInfo.getQrcodeLink() == null)
        || (partyShareInfo.getQrcodeLink().equals(""))) {
      Subscription subscribe = ActivityModel.getInstance()
          .get_party_share_content(id+"")
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<ActivityBean>() {
            @Override public void onNext(ActivityBean activityBean) {

              if (null != activityBean) {

                JUtils.Log(TAG,
                    "saveTowDimenCode : Qrcodelink=" + partyShareInfo.getQrcodeLink());

                try {
                  WebView webView = new WebView(CommonWebViewActivity.this);
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
                  webView.loadUrl(partyShareInfo.getQrcodeLink());
                  View cv = getWindow().getDecorView();
                  Bitmap bmp = catchWebScreenshot(webView, cv.getWidth(), cv.getHeight(),
                          partyShareInfo.getQrcodeLink(), null);
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
      addSubscription(subscribe);
    } else {
      JUtils.Log(TAG, "saveTowDimenCode : Qrcodelink=" + partyShareInfo.getQrcodeLink());
      try {
        WebView webView = new WebView(CommonWebViewActivity.this);
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

        webView.loadUrl(partyShareInfo.getQrcodeLink());
        //Bitmap bmp= captureWebView(webView);
        View cv = getWindow().getDecorView();
        Bitmap bmp = catchWebScreenshot(webView, cv.getWidth(), cv.getHeight(),
                partyShareInfo.getQrcodeLink(), null);
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
                + getResources().getString(R.string.share_2dimen_pic_name)
                + ".jpg";
            BitmapUtil.saveBitmap(b, fileName);
            Toast.makeText(CommonWebViewActivity.this, R.string.share_2dimen_pic_tips,
                Toast.LENGTH_SHORT).show();

            File file = new File(fileName);
            Uri uri = Uri.fromFile(file);
            // 通知图库更新
            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            sendBroadcast(scannerIntent);
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

  public void get_party_share_content(String id) {
    JUtils.Log(TAG, "get_party_share_content id "+id);

    Subscription subscribe = ActivityModel.getInstance()
            .get_party_share_content(id)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<ActivityBean>() {
              @Override public void onNext(ActivityBean activityBean) {

                if (null != activityBean) {
                  partyShareInfo = activityBean;
                  partyShareInfo.setQrcodeLink(activityBean.getQrcodeLink());

                  JUtils.Log(TAG, "partyShareInfo: desc="
                          + partyShareInfo.getActiveDec()
                          + " "
                          + "qrcode="
                          + partyShareInfo.getQrcodeLink()
                          + " title="
                          + partyShareInfo.getTitle());
                }
              }
            });
    addSubscription(subscribe);
  }

  private void sharePartyInfo() {
    if (partyShareInfo == null) return;

    JUtils.Log(TAG, " title =" + partyShareInfo.getTitle());
    JUtils.Log(TAG, " desc="
        + partyShareInfo.getActiveDec()
        + " url="
        + partyShareInfo.getShareLink());

    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    oks.setTitle(partyShareInfo.getTitle());
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    oks.setTitleUrl(partyShareInfo.getShareLink());
    // text是分享文本，所有平台都需要这个字段
    oks.setText(partyShareInfo.getActiveDec() + partyShareInfo.getShareLink());
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    //oks.setImagePath(filePara.getFilePath());//确保SDcard下面存在此张图片
    //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
    oks.setImageUrl(partyShareInfo.getShareIcon());
    oks.setUrl(partyShareInfo.getShareLink());

    // url仅在微信（包括好友和朋友圈）中使用
    //oks.setUrl(myurl);
    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    //oks.setComment("我是测试评论文本");
    // site是分享此内容的网站名称，仅在QQ空间使用
    //oks.setSite(getString(R.string.app_name));
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    //oks.setSiteUrl("http://sharesdk.cn");

    // 启动分享GUI
    oks.show(this);
  }

  public void jumpToNativeLocation(String url) {
    //if (!TextUtils.isEmpty(url)) {
    //Bundle bundle = new Bundle();
    //bundle.putString("product_id", url);
    //Intent intent = new Intent(mContext, ProductDetailActvityWeb.class);
    //intent.putExtras(bundle);
    //startActivity(intent);
    //JUtils.Log(TAG, url+"aaaa");
    JumpUtils.push_jump_proc(this, url);
    //}
  }

  /*private class CancelListener implements DialogInterface.OnCancelListener,
          DialogInterface.OnClickListener {

    @Override
    public void onCancel(DialogInterface dialogInterface) {
      mResult.cancel();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
      mResult.cancel();
    }
  }

  private class PositiveListener implements DialogInterface.OnClickListener {

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
      mResult.confirm();
    }
  }*/
}
