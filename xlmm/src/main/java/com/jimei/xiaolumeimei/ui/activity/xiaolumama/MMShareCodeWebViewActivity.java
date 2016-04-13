package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.Menu;
import android.view.MenuItem;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */

public class MMShareCodeWebViewActivity extends CommonWebViewActivity {

//    private String title, sharelink, desc, shareimg;
    @Override
    protected void initViews() {
        super.initViews();
      webviewTitle.setText("我的邀请");
    }

    @Override
    protected void initData() {
        super.initData();
//        MMProductModel.getInstance()
//                .getShareShopping()
//                .subscribeOn(Schedulers.io())
//                .subscribe(new ServiceResponse<MMShoppingBean>() {
//
//                    @Override public void onNext(MMShoppingBean mmShoppingBean) {
//
//                        if (null != mmShoppingBean) {
//                            title = (String) mmShoppingBean.getShopInfo().getName();
//                            sharelink = mmShoppingBean.getShopInfo().getPreview_shop_link();
//                            shareimg = mmShoppingBean.getShopInfo().getThumbnail();
//                            desc = mmShoppingBean.getShopInfo().getDesc();
//                        }
//                    }
//                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (item.getItemId() == R.id.action_share) {
//            share_shopping(title, sharelink, desc, shareimg);
//        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
