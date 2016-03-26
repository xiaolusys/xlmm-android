package com.jimei.xiaolumeimei.htmlJsBridge;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.webkit.JavascriptInterface;

import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.ui.activity.main.WebViewActivity;
import com.jude.utils.JUtils;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/16.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class AndroidJsBridge {

  private WebViewActivity webViewActivity;

  public AndroidJsBridge(WebViewActivity webViewActivity) {
    this.webViewActivity = webViewActivity;
  }

  @JavascriptInterface public void getPromotionParams(String uform, String activity_id) {
    JUtils.Log("WebViewActivity", uform + "======" + activity_id);
    webViewActivity.getPromotionParams(uform, activity_id);
  }

  @JavascriptInterface public void callNativeShareFunc(String uform, String activity_id) {
    JUtils.Log("WebViewActivity", uform + "======activity_id =" + activity_id);
    webViewActivity.getPromotionParams(uform, activity_id);
  }

  @JavascriptInterface public void saveTwoDimenCode() {
    JUtils.Log("WebViewActivity", "saveTowDimenCode");
    webViewActivity.saveTwoDimenCode();
  }

  @JavascriptInterface public void jumpToNativeLocation(String url) {
    JUtils.Log("WebViewActivity", url);
    webViewActivity.jumpToNativeLocation(url);
  }

  @JavascriptInterface public String getNativeMobileSNCode(){
    return ((TelephonyManager) XlmmApp.getInstance().getSystemService(
            Context.TELEPHONY_SERVICE)).getDeviceId();
  }
}
