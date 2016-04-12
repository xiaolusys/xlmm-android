package com.jimei.xiaolumeimei.htmlJsBridge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
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
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.utils.BitmapUtil;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.io.File;

import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/16.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class AndroidJsBridge {

  private static final String TAG = "AndroidJsBridge";

  private ActivityBean partyShareInfo;
  private Context mContext;

  public AndroidJsBridge(Context context) {
    //this.commonWebViewActivity = commonWebViewActivity;
    this.mContext = context;
  }


  @JavascriptInterface public void callNativeShareFunc(String uform, String activity_id) {
    JUtils.Log("CommonWebViewActivity", uform + "======activity_id =" + activity_id);
    getPromotionParams(uform, activity_id);
  }


  @JavascriptInterface public void jumpToNativeLocation(String url) {
    JUtils.Log("CommonWebViewActivity", url);
    jumpToNativeLocation(url);
  }

  @JavascriptInterface public String getNativeMobileSNCode(){
    return ((TelephonyManager) XlmmApp.getInstance().getSystemService(
            Context.TELEPHONY_SERVICE)).getDeviceId();
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
                  JUtils.Log(TAG, "getPromotionParams get_share_content: uform="
                          + uform
                  );

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
                  }else if(uform.equals("")){
                    sharePartyInfo();
                  }
                }
              }
            });
    ((BaseSwipeBackCompatActivity)mContext).addSubscription(subscribe);
  }

  private void share_wxapp(String activity_id) {
    if (partyShareInfo == null) return;

    Platform.ShareParams sp = new Platform.ShareParams();

    sp.setTitle(partyShareInfo.getTitle());
    sp.setText(partyShareInfo.getActiveDec()+partyShareInfo.getShareLink());

    sp.setUrl(partyShareInfo.getShareLink());
    sp.setShareType(Platform.SHARE_WEBPAGE);
    sp.setImageUrl(partyShareInfo.getShareIcon());

    Platform wx = ShareSDK.getPlatform(mContext, Wechat.NAME);
    wx.setPlatformActionListener((PlatformActionListener)mContext); // 设置分享事件回调
    // 执行图文分享
    wx.share(sp);
  }

  private void share_pyq(String activity_id) {

    if (partyShareInfo == null) return;

    JUtils.Log(TAG, "title:"+partyShareInfo.getTitle() +" "+partyShareInfo.getShareIcon());

    WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
    //sp.setImageUrl(linkQrcode);
    sp.setTitle(partyShareInfo.getTitle());
    //sp.setText(shareInfo.getActiveDec() + " http://m.xiaolumeimei.com/" + myurl +
    //    "&ufrom=" + ufrom);
    //sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setShareType(Platform.SHARE_WEBPAGE);
    sp.setImageUrl(partyShareInfo.getShareIcon());

    Platform pyq = ShareSDK.getPlatform(mContext, WechatMoments.NAME);
    pyq.setPlatformActionListener((PlatformActionListener)mContext); // 设置分享事件回调
    // 执行图文分享
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
    qq.setPlatformActionListener((PlatformActionListener)mContext); // 设置分享事件回调
    // 执行图文分享
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
    qzone.setPlatformActionListener((PlatformActionListener)mContext); // 设置分享事件回调
    // 执行图文分享
    qzone.share(sp);
  }

  private void share_sina(String activity_id) {
    if (partyShareInfo == null) return;

    get_party_share_content(activity_id);
    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
    //sp.setTitle(title);
    //sp.setTitleUrl("http://m.xiaolumeimei.com/" + myurl + "&ufrom=" + ufrom);
    sp.setText(partyShareInfo.getActiveDec()
            + partyShareInfo.getShareLink());
    sp.setImageUrl(partyShareInfo.getShareIcon());

    Platform weibo = ShareSDK.getPlatform(mContext, SinaWeibo.NAME);
    weibo.setPlatformActionListener((PlatformActionListener)mContext); // 设置分享事件回调
    // 执行图文分享
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
    ((BaseSwipeBackCompatActivity)mContext).addSubscription(subscribe);
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
    oks.show(mContext);
  }

  public void saveTwoDimenCode(Context context) {

    if ((partyShareInfo == null)
            || (partyShareInfo.getQrcodeLink() == null)
            || (partyShareInfo.getQrcodeLink().equals(""))) {
      JUtils.Log(TAG, "saveTowDimenCode : fail,Qrcodelink=null" );
      return;
    } else {
      JUtils.Log(TAG, "saveTowDimenCode : Qrcodelink=" + partyShareInfo.getQrcodeLink());
      try {
        WebView webView = new WebView(context);
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
        View cv = ((BaseSwipeBackCompatActivity)mContext).getWindow().getDecorView();
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
   * 抓取WEB界面的截屏
   *
   * @param containerWidth 截屏宽度，也就放置WebView的宽度
   * @param containerHeight 截屏高度，也就放置WebView的高度
   * @param baseUrl Base Url
   * @param context activity context
   */
  public Bitmap catchWebScreenshot(final WebView w, final int containerWidth,
                                   final int containerHeight, final String baseUrl, final Context context) {
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
                    + context.getResources().getString(R.string.share_2dimen_pic_name)
                    + ".jpg";
            BitmapUtil.saveBitmap(b, fileName);
            Toast.makeText(context, R.string.share_2dimen_pic_tips,
                    Toast.LENGTH_SHORT).show();

            File file = new File(fileName);
            Uri uri = Uri.fromFile(file);
            // 通知图库更新
            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            context.sendBroadcast(scannerIntent);
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
}
