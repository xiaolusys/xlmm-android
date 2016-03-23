package com.jimei.xiaolumeimei.htmlJsBridge;

import android.webkit.JavascriptInterface;
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

  @JavascriptInterface public void getPromotionParams(String uform, String share_link) {
    JUtils.Log("WebViewActivity", uform + "======" + share_link);
    webViewActivity.getPromotionParams(uform, share_link);
  }

  @JavascriptInterface public void saveTwoDimenCode() {
    JUtils.Log("WebViewActivity", "saveTowDimenCode");
    webViewActivity.saveTwoDimenCode();
  }

  @JavascriptInterface public void getProductId(String id) {
    webViewActivity.getProductId(id);
  }
}
