package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.MenuItem;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class MMStoreWebViewActivity extends CommonWebViewActivity {

    private String title, sharelink, desc, shareimg;

    @Override
    protected void initViews() {
        super.initViews();
        titleView.setName("我的店铺");
    }

    @Override
    protected void initData() {
        super.initData();
        addSubscription(XlmmApp.getVipInteractor(this)
            .getShareShopping()
            .subscribe(new ServiceResponse<MMShoppingBean>() {

                @Override
                public void onNext(MMShoppingBean mmShoppingBean) {

                    if (null != mmShoppingBean) {
                        try {
                            title = mmShoppingBean.getShopInfo().getName();
                            sharelink = mmShoppingBean.getShopInfo().getShopLink();
                            shareimg = mmShoppingBean.getShopInfo().getThumbnail();
                            desc = mmShoppingBean.getShopInfo().getDesc();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            share_shopping(title, sharelink, desc, shareimg);
            MobclickAgent.onEvent(this, "Mama_shop_share");
        }
        return true;
    }
}
