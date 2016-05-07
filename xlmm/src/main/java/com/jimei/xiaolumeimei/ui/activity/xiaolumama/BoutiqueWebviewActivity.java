package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.MenuItem;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/25.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class BoutiqueWebviewActivity extends CommonWebViewActivity {

  private String title,link,desc,img;

  @Override protected void initViews() {
    super.initViews();
    webviewTitle.setText("精品活动");
  }

  @Override
  protected void initData() {
    super.initData();
    Subscription subscribe = MamaInfoModel.getInstance()
            .getMamaFortune()
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<MamaFortune>() {
              @Override
              public void onNext(MamaFortune mamaFortune) {
                title = "精选活动";
                link = mamaFortune.getMama_fortune().getmMamaEventLink();
                desc = "更多精选活动,尽在小鹿美美~~";
                img = "http://7xogkj.com2.z0.glb.qiniucdn.com/1181123466.jpg";
              }
            });
    addSubscription(subscribe);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_share) {
      share_shopping(title, link, desc, img);
    }
    return true;
  }
}
