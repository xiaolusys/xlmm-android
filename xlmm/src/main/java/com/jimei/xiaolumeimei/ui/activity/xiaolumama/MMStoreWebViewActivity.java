package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.MenuItem;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import rx.schedulers.Schedulers;

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
        webviewTitle.setText("我的店铺");
    }

    @Override
    protected void initData() {
        super.initData();
        MMProductModel.getInstance()
                .getShareShopping()
                .subscribeOn(Schedulers.io())
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
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            share_shopping(title, sharelink, desc, shareimg);
        }
        return true;
    }

    @Override
    public void sharePartyInfo() {
        super.sharePartyInfo();
        MobclickAgent.onEvent(this,"Shop_share");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
