package com.jimei.xiaolumeimei.htmlJsBridge.modules;

import android.content.Context;
import com.jimei.xiaolumeimei.ui.activity.product.ProductPopDetailActvityWeb;

/**
 * Created by itxuye(www.itxuye.com) on 2016/05/17.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class UiPlugin {
  private static final String TAG = UiPlugin.class.getCanonicalName();

  private Context mContext;

  public UiPlugin(Context context) {
    mContext = context;
  }

  public void showSkuPopup(String json) {
    ((ProductPopDetailActvityWeb) mContext).showPop(json);
  }
}
