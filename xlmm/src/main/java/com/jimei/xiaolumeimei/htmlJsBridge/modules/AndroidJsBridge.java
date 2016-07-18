package com.jimei.xiaolumeimei.htmlJsBridge.modules;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.google.gson.Gson;
import com.jimei.xiaolumeimei.BuildConfig;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.CallNativeFuncBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.ui.activity.product.ProductPopDetailActvityWeb;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShareCodeWebViewActivity;
import com.jimei.xiaolumeimei.utils.CameraUtils;
import com.jimei.xiaolumeimei.utils.FileUtils;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.mob.tools.utils.UIHandler;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/16.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class AndroidJsBridge implements PlatformActionListener, Handler.Callback {

  private static final int MSG_ACTION_CCALLBACK = 2;

  private static final String TAG = "AndroidJsBridge";

  private ActivityBean partyShareInfo;
  private BaseSwipeBackCompatActivity mContext;
  private Bitmap bitmap;

  public AndroidJsBridge(BaseSwipeBackCompatActivity context) {
    //this.commonWebViewActivity = commonWebViewActivity;
    this.mContext = context;
  }

  @JavascriptInterface public void callNativeShareFunc(String uform, String activity_id) {
    JUtils.Log("CommonWebViewActivity", uform + "======activity_id =" + activity_id);
    getPromotionParams(uform, activity_id);
  }

  @JavascriptInterface public void jumpToNativeLocation(String url) {
    JUtils.Log("CommonWebViewActivity", url);
    jump_ToNativeLocation(url);
  }

  @JavascriptInterface public String getNativeMobileSNCode() {
    return "";
  }

  @JavascriptInterface public void showLoading(String isShow) {
    if (isShow.contains("true")) {
      mContext.showIndeterminateProgressDialog(false);
    } else if (isShow.contains("false")) {
      mContext.hideIndeterminateProgressDialog();
    }
  }

  @JavascriptInterface public void changeId(String str) {
    ((MMShareCodeWebViewActivity) mContext).setId(str);
  }

  public void getPromotionParams(String uform, String activity_id) {

    Subscription subscribe = ActivityModel.getInstance()
        .get_party_share_content(activity_id)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ActivityBean>() {
          @Override public void onNext(ActivityBean activityBean) {

            if (null != activityBean) {
              partyShareInfo = activityBean;
              partyShareInfo.setQrcodeLink(activityBean.getQrcodeLink());
              JUtils.Log(TAG, "getPromotionParams get_share_content: activeDec="
                  +
                  partyShareInfo.getActiveDec()
                  + " linkQrcode="
                  + partyShareInfo.getQrcodeLink()
                  + " "
                  + "title="
                  + partyShareInfo.getTitle());
              JUtils.Log(TAG, "getPromotionParams get_share_content: uform=" + uform);

              if (uform.equals("wxapp")) {
                share_wxapp(activity_id);
              } else if (uform.equals("pyq")) {
                share_pyq(activity_id);
              } else if (uform.equals("qq")) {

                share_qq(activity_id);
              } else if (uform.equals("qqspa")) {
                share_qqspa(activity_id);
              } else if (uform.equals("sinawb")) {
                share_sina(activity_id);
              } else if (uform.equals("web")) {
                saveTwoDimenCode(mContext);
              } else if (uform.equals("")) {
                sharePartyInfo();
              }
            }
          }
        });
    ((BaseSwipeBackCompatActivity) mContext).addSubscription(subscribe);
  }

  private void share_wxapp(String activity_id) {
    if (partyShareInfo == null) return;
    JUtils.Log(TAG, partyShareInfo.toString());
    Platform.ShareParams sp = new Platform.ShareParams();

    sp.setTitle(partyShareInfo.getTitle());
    sp.setText(partyShareInfo.getActiveDec() + partyShareInfo.getShareLink());

    sp.setUrl(partyShareInfo.getShareLink());
    sp.setShareType(Platform.SHARE_WEBPAGE);
    sp.setImageUrl(partyShareInfo.getShareIcon());

    Platform wx = ShareSDK.getPlatform(mContext, Wechat.NAME);
    wx.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享

    Map<String, String> map = new HashMap<String, String>();
    map.put("id", "name");
    map.put(wx.getId()+ "", wx.getName());
    MobclickAgent.onEvent(mContext, "ShareID", map);

    wx.share(sp);
  }

  private void share_pyq(String activity_id) {

    if (partyShareInfo == null) return;

    //    JUtils.Log(TAG, "title:"+partyShareInfo.getTitle() +" "+partyShareInfo.getShareIcon());
    JUtils.Log(TAG, partyShareInfo.toString());
    WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
    //sp.setImageUrl(linkQrcode);
    sp.setTitle(partyShareInfo.getTitle());
    //sp.setText(shareInfo.getActiveDec() + " http://m.xiaolumeimei.com/" + myurl +
    //    "&ufrom=" + ufrom);
    //sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setImageUrl(partyShareInfo.getShareIcon());
    sp.setShareType(Platform.SHARE_WEBPAGE);
    Platform pyq = ShareSDK.getPlatform(mContext, WechatMoments.NAME);
    pyq.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享

    Map<String, String> map = new HashMap<String, String>();
    map.put("id", "name");
    map.put(pyq.getId()+ "", pyq.getName());
    MobclickAgent.onEvent(mContext, "ShareID", map);
    pyq.share(sp);
  }

  private void share_qq(String activity_id) {
    if (partyShareInfo == null) return;

    get_party_share_content(activity_id);
    QQ.ShareParams sp = new QQ.ShareParams();
    sp.setTitle(partyShareInfo.getTitle());

    sp.setText(partyShareInfo.getActiveDec());
    sp.setImageUrl(partyShareInfo.getShareIcon());

    sp.setTitleUrl(partyShareInfo.getShareLink());

    Platform qq = ShareSDK.getPlatform(mContext, QQ.NAME);
    qq.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享

    Map<String, String> map = new HashMap<String, String>();
    map.put("id", "name");
    map.put(qq.getId()+ "", qq.getName());
    MobclickAgent.onEvent(mContext, "ShareID", map);

    qq.share(sp);
  }

  private void share_qqspa(String activity_id) {
    if (partyShareInfo == null) return;

    get_party_share_content(activity_id);
    QZone.ShareParams sp = new QZone.ShareParams();
    sp.setTitle(partyShareInfo.getTitle());
    // 标题的超链接
    sp.setTitleUrl(partyShareInfo.getShareLink());
    sp.setText(partyShareInfo.getActiveDec());
    sp.setImageUrl(partyShareInfo.getShareIcon());
    //sp.setSite("发布分享的网站名称");
    sp.setSiteUrl(partyShareInfo.getShareLink());

    Platform qzone = ShareSDK.getPlatform(mContext, QZone.NAME);
    qzone.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享

    Map<String, String> map = new HashMap<String, String>();
    map.put("id", "name");
    map.put(qzone.getId()+ "", qzone.getName());
    MobclickAgent.onEvent(mContext, "ShareID", map);
    qzone.share(sp);
  }

  private void share_sina(String activity_id) {
    if (partyShareInfo == null) return;

    get_party_share_content(activity_id);
    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
    //sp.setTitle(title);
    //sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setText(partyShareInfo.getActiveDec() + partyShareInfo.getShareLink());
    sp.setImageUrl(partyShareInfo.getShareIcon());

    Platform weibo = ShareSDK.getPlatform(mContext, SinaWeibo.NAME);
    weibo.setPlatformActionListener((PlatformActionListener) mContext); // 设置分享事件回调
    // 执行图文分享

    Map<String, String> map = new HashMap<String, String>();
    map.put("id", "name");
    map.put(weibo.getId()+ "", weibo.getName());
    MobclickAgent.onEvent(mContext, "ShareID", map);
    weibo.share(sp);
  }

  /*public void get_share_content(String ufrom) {
    Subscription subscribe = ActivityModel.getInstance()
        .get_share_content(ufrom)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ActivityBean>() {
          @Override public void onNext(ActivityBean activityBean) {

            if (null != activityBean) {
              shareInfo = activityBean;
              shareInfo.setQrcodeLink(activityBean.getQrcodeLink());

              JUtils.Log(TAG, "get_share_content: desc="
                  + shareInfo.getActiveDec()
                  + " "
                  + "qrcode="
                  + shareInfo.getQrcodeLink()
                  + " title="
                  + shareInfo.getTitle());
            }
          }
        });
    addSubscription(subscribe);
  }*/

  public void get_party_share_content(String id) {
    JUtils.Log(TAG, "get_party_share_content id " + id);

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
    ((BaseSwipeBackCompatActivity) mContext).addSubscription(subscribe);
  }


  private void sharePartyInfo() {
    if (partyShareInfo == null) return;

    JUtils.Log(TAG, " title =" + partyShareInfo.getTitle());
    JUtils.Log(TAG,
        " desc=" + partyShareInfo.getActiveDec() + " url=" + partyShareInfo.getShareLink());

    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    oks.setTitle(partyShareInfo.getTitle());
    oks.setTitleUrl(partyShareInfo.getShareLink());
    oks.setText(partyShareInfo.getActiveDec() + partyShareInfo.getShareLink());
    oks.setImageUrl(partyShareInfo.getShareIcon());
    oks.setUrl(partyShareInfo.getShareLink());

    Bitmap enableLogo =
        BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ssdk_oks_logo_copy);
    String label = "复制链接";

    View.OnClickListener listener = new View.OnClickListener() {
      public void onClick(View v) {
        //if (shareProductBean.getShareLink()) {
        //}
        //saveTwoDimenCode(mContext);

        copy(partyShareInfo.getShareLink(), mContext);
        JUtils.Toast("文字已经复制");
      }
    };
    oks.setCustomerLogo(enableLogo, label, listener);
    // 启动分享GUI
    oks.show(mContext);
  }

  public void saveTwoDimenCode(Context context) {

    if ((partyShareInfo == null)
        || (partyShareInfo.getQrcodeLink() == null)
        || (partyShareInfo.getQrcodeLink().equals(""))) {
      JUtils.Log(TAG, "saveTowDimenCode : fail,Qrcodelink=null");
      return;
    } else {
      JUtils.Log(TAG, "saveTowDimenCode : Qrcodelink=" + partyShareInfo.getQrcodeLink());
      try {
        WebView webView = new WebView(context);
        webView.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
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
        //webView.setDrawingCacheEnabled(true);

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
        View cv = ((BaseSwipeBackCompatActivity) mContext).getWindow().getDecorView();
        Bitmap bmp = catchWebScreenshot(webView, cv.getWidth(), cv.getHeight(),
            partyShareInfo.getShareLink(), mContext);
        /*String fileName = Environment.getExternalStorageDirectory()
            + "/"
            + Environment.DIRECTORY_DCIM
            + "/Camera/小鹿美美活动二维码.jpg";
        saveBitmap(bmp, fileName);*/

        RxPermissions.getInstance(context)
            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe(granted -> {
              if (granted) {
                String fileName = Environment.getExternalStorageDirectory() +
                    CameraUtils.XLMM_IMG_PATH + "/webview_capture1" + ".jpg";

                if (FileUtils.isFileExist(fileName)) {
                  FileUtils.deleteFile(fileName);
                }

                try {
                  FileOutputStream fos = new FileOutputStream(fileName);
                  //压缩bitmap到输出流中
                  bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                  fos.flush();
                  fos.close();
                } catch (IOException e) {
                  e.printStackTrace();
                }

                Uri uri = Uri.fromFile(new File(fileName));
                // 通知图库更新
                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                scannerIntent.setData(uri);

                context.sendBroadcast(scannerIntent);
                JUtils.Log(TAG, "filename===" + FileUtils.isFileExist(fileName));
                Toast.makeText(context, "截取快照成功至/xlmm/xiaolumeimei", Toast.LENGTH_LONG).show();
              } else {
                // Oups permission denied
                JUtils.Toast("小鹿美美需要存储权限存储图片,请再次点击保存并打开权限许可.");
              }
            });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 抓取WEB界面的截屏
   *
   * @param containerWidth 截屏宽度，也就放置WebView的宽度
   * @param containerHeight 截屏高度，也就放置WebView的高度
   * @param baseUrl Base Url
   * @param context activity context
   */
  public Bitmap catchWebScreenshot(final WebView w, final int containerWidth,
      final int containerHeight, final String baseUrl, final Context context) {
    final Bitmap b = Bitmap.createBitmap(containerWidth, containerHeight, Bitmap.Config.ARGB_8888);
    w.post(new Runnable() {
      public void run() {
        w.setWebViewClient(new WebViewClient() {
          @Override public void onPageFinished(WebView view, String url) {
            JUtils.Log(TAG, "onPageFinished URL=" + url);

            //String fileName = Environment.getExternalStorageDirectory()
            //    + "/"
            //    + Environment.DIRECTORY_DCIM
            //    + "/Camera/"
            //    + context.getResources().getString(R.string.share_2dimen_pic_name)
            //    + ".jpg";
            //BitmapUtil.saveBitmap(b, fileName);

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

  @Override public void onCancel(Platform platform, int action) {
    // 取消
    Message msg = new Message();
    msg.what = MSG_ACTION_CCALLBACK;
    msg.arg1 = 3;
    msg.arg2 = action;
    msg.obj = platform;
    UIHandler.sendMessage(msg, this);
  }

  @Override public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
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
        Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
        JUtils.Log(TAG, "分享回调成功------------");
      }
      break;
      case 2: {
        // 失败
        Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
      }
      break;
      case 3: {
        // 取消
        Toast.makeText(mContext, "分享取消", Toast.LENGTH_SHORT).show();
      }
      break;
    }

    return false;
  }

  public void jump_ToNativeLocation(String url) {
    //if (!TextUtils.isEmpty(url)) {
    //Bundle bundle = new Bundle();
    //bundle.putString("product_id", url);
    //Intent intent = new Intent(mContext, ProductDetailActvityWeb.class);
    //intent.putExtras(bundle);
    //startActivity(intent);
    JUtils.Log(TAG, "jump_ToNativeLocation=====" + url);
    if (!TextUtils.isEmpty(url)) {
      JumpUtils.jumToProc(mContext, JumpUtils.get_jump_info(url));
    }
    //}
  }

  public void copy(String content, Context context) {
    // 得到剪贴板管理器
    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    cmb.setText(content.trim());
  }

  @JavascriptInterface public void callNativeLoginActivity(String pageUrl) {
    JUtils.Log(TAG,"pageUrl"+pageUrl);
    Intent intent = new Intent(mContext, LoginActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString("login", "h5");
    bundle.putString("actlink", pageUrl);
    intent.putExtras(bundle);
    mContext.startActivity(intent);
    mContext.finish();
  }

  @JavascriptInterface public void showSkuPopup(String json) {
    ((ProductPopDetailActvityWeb) mContext).showPop(json);
  }

  @JavascriptInterface public void jumpToNativeLogin() {
    ((ProductPopDetailActvityWeb) mContext).jumToNativeLogin();
  }

  @JavascriptInterface public void callNativeBack() {
    mContext.finish();
  }

  @JavascriptInterface public void callNativeUniShareFunc(String json) {
    JUtils.Log(TAG, "callNativeUniShareFunc====" + json);
    if (!TextUtils.isEmpty(json)) {
      Gson gson = new Gson();
      CallNativeFuncBean callNativeFuncBean = gson.fromJson(json, CallNativeFuncBean.class);
      if (null != callNativeFuncBean) {
        if (!TextUtils.isEmpty(callNativeFuncBean.getShareTo()) && callNativeFuncBean.getShareTo()
            .equals("wxapp")) {
          shareToWx(callNativeFuncBean);
        } else if (!TextUtils.isEmpty(callNativeFuncBean.getShareTo())
            && callNativeFuncBean.getShareTo().equals("pyq")) {
          shareToPyq(callNativeFuncBean);
        } else if (!TextUtils.isEmpty(callNativeFuncBean.getShareTo())
            && callNativeFuncBean.getShareTo().equals("qq")) {
          shareToQq(callNativeFuncBean);
        } else if (!TextUtils.isEmpty(callNativeFuncBean.getShareTo())
            && callNativeFuncBean.getShareTo().equals("qqspa")) {
          shareToQQspa(callNativeFuncBean);
        } else if (!TextUtils.isEmpty(callNativeFuncBean.getShareTo())
            && callNativeFuncBean.getShareTo().equals("sinawb")) {
          shareToSina(callNativeFuncBean);
        } else if (!TextUtils.isEmpty(callNativeFuncBean.getShareTo())
            && callNativeFuncBean.getShareTo().equals("web")) {
          saveTwoDimenCode(mContext);
        } else if (TextUtils.isEmpty(callNativeFuncBean.getShareTo())) {
          share(callNativeFuncBean.getShareTitle(), callNativeFuncBean.getLink(),
              callNativeFuncBean.getShareDesc(), callNativeFuncBean.getShareIcon());
        }
      }
    }
  }

  private void shareToSina(CallNativeFuncBean callNativeFuncBean) {
    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();

    sp.setText(callNativeFuncBean.getShareDesc());
    sp.setImageUrl(callNativeFuncBean.getShareIcon());

    Platform weibo = ShareSDK.getPlatform(mContext, SinaWeibo.NAME);
    weibo.setPlatformActionListener((PlatformActionListener) mContext); // 设置分享事件回调
    // 执行图文分享

    Map<String, String> map = new HashMap<String, String>();
    map.put("id", "name");
    map.put(weibo.getId()+ "", weibo.getName());
    MobclickAgent.onEvent(mContext, "ShareID", map);
    weibo.share(sp);
  }

  private void shareToQQspa(CallNativeFuncBean callNativeFuncBean) {
    QZone.ShareParams sp = new QZone.ShareParams();
    sp.setTitle(callNativeFuncBean.getShareTitle());
    // 标题的超链接
    sp.setTitleUrl(callNativeFuncBean.getLink());
    sp.setText(callNativeFuncBean.getShareDesc());
    sp.setImageUrl(callNativeFuncBean.getShareIcon());
    //sp.setSite("发布分享的网站名称");
    sp.setSiteUrl(callNativeFuncBean.getLink());

    Platform qzone = ShareSDK.getPlatform(mContext, QZone.NAME);
    qzone.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享
    Map<String, String> map = new HashMap<String, String>();
    map.put("id", "name");
    map.put(qzone.getId()+ "", qzone.getName());
    MobclickAgent.onEvent(mContext, "ShareID", map);
    qzone.share(sp);
  }

  private void shareToQq(CallNativeFuncBean callNativeFuncBean) {
    QQ.ShareParams sp = new QQ.ShareParams();
    sp.setTitle(callNativeFuncBean.getShareTitle());

    sp.setText(callNativeFuncBean.getShareDesc());
    sp.setImageUrl(callNativeFuncBean.getShareIcon());

    sp.setTitleUrl(callNativeFuncBean.getLink());

    Platform qq = ShareSDK.getPlatform(mContext, QQ.NAME);
    qq.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享

    Map<String, String> map = new HashMap<String, String>();
    map.put("id", "name");
    map.put(qq.getId()+ "", qq.getName());
    MobclickAgent.onEvent(mContext, "ShareID", map);
    qq.share(sp);
  }

  private void shareToPyq(CallNativeFuncBean callNativeFuncBean) {
    WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
    //sp.setImageUrl(linkQrcode);
    sp.setTitle(callNativeFuncBean.getShareTitle());
    sp.setImageUrl(callNativeFuncBean.getShareIcon());
    sp.setShareType(Platform.SHARE_WEBPAGE);
    Platform pyq = ShareSDK.getPlatform(mContext, WechatMoments.NAME);
    pyq.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享
    Map<String, String> map = new HashMap<String, String>();
    map.put("id", "name");
    map.put(pyq.getId()+ "", pyq.getName());
    MobclickAgent.onEvent(mContext, "ShareID", map);
    pyq.share(sp);
  }

  private void shareToWx(CallNativeFuncBean callNativeFuncBean) {
    Platform.ShareParams sp = new Platform.ShareParams();

    sp.setTitle(callNativeFuncBean.getShareTitle());
    sp.setText(callNativeFuncBean.getShareDesc());

    sp.setUrl(callNativeFuncBean.getLink());
    sp.setShareType(Platform.SHARE_WEBPAGE);
    sp.setImageUrl(callNativeFuncBean.getShareIcon());

    Platform wx = ShareSDK.getPlatform(mContext, Wechat.NAME);
    wx.setPlatformActionListener(this); // 设置分享事件回调
    // 执行图文分享

    Map<String, String> map = new HashMap<String, String>();
    map.put("id", "name");
    map.put(wx.getId()+ "", wx.getName());
    MobclickAgent.onEvent(mContext, "ShareID", map);

    wx.share(sp);
  }

  protected void share(String title, String sharelink, String desc, String shareimg) {
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

    Bitmap enableLogo =
        BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ssdk_oks_logo_copy);
    String label = "二维码";

    View.OnClickListener listener = new View.OnClickListener() {
      public void onClick(View v) {
        //if (shareProductBean.getShareLink()) {
        //}
        saveTwoDimenCode(mContext);
      }
    };
    oks.setCustomerLogo(enableLogo, label, listener);
    // 启动分享GUI
    oks.setShareContentCustomizeCallback(new ShareContentCustom(desc));
    oks.show(mContext);
  }

  @JavascriptInterface public String appVersion() {
    return String.valueOf(BuildConfig.VERSION_CODE);
  }

  class ShareContentCustom implements ShareContentCustomizeCallback {

    private String text;

    public ShareContentCustom(String text) {
      this.text = text;
    }

    @Override public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("id", "name");
      map.put(platform.getId()+ "", platform.getName());
      JUtils.Log("ShareID",platform.getId()+ "    "+platform.getName());
      MobclickAgent.onEvent(mContext, "ShareID", map);

      if (WechatMoments.NAME.equals(platform.getName())) {
        paramsToShare.setTitle(text);
      }
    }
  }
}
