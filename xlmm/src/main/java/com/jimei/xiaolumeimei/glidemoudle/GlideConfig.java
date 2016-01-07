package com.jimei.xiaolumeimei.glidemoudle;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class GlideConfig implements GlideModule {

  //修改glide加载图片格式为PREFER_ARGB_8888
  @Override public void applyOptions(Context context, GlideBuilder builder) {
    builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
  }

  @Override public void registerComponents(Context context, Glide glide) {

  }
}
