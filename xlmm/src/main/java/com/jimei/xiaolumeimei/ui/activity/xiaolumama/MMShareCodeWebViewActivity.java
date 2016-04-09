package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class MMShareCodeWebViewActivity extends CommonWebViewActivity {

    private String actlink;
    private String domain;
    private String sessionid;
    private String title, sharelink, desc, shareimg;


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
                            title = (String) mmShoppingBean.getShopInfo().getName();
                            sharelink = mmShoppingBean.getShopInfo().getShopLink();
                            shareimg = mmShoppingBean.getShopInfo().getThumbnail();
                            desc = mmShoppingBean.getShopInfo().getDesc();
                        }
                    }
                });

    }

    @Override
    protected void initViews() {
        super.initViews();
        webviewTitle.setText("我的邀请");
    }
}
