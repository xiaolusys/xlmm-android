package com.jimei.xiaolumeimei.htmlJsBridge;

import android.webkit.JavascriptInterface;
import android.widget.Toast;
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
    JUtils.Log("ActivityPromotion", uform + "======" + share_link);
    Toast.makeText(webViewActivity, uform + "======" + share_link, Toast.LENGTH_SHORT)
        .show();
    webViewActivity.getPromotionParams(uform, share_link);
  }
}
