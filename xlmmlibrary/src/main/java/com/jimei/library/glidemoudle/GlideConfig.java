package com.jimei.library.glidemoudle;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class GlideConfig implements GlideModule {

  //修改glide加载图片格式为PREFER_ARGB_8888
  @Override public void applyOptions(Context context, GlideBuilder builder) {
    builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
  }

  //替换网络层为okhttp
  @Override public void registerComponents(Context context, Glide glide) {
    //glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
  }
}
