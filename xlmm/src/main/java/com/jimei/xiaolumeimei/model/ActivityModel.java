package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.BrandListBean;
import com.jimei.xiaolumeimei.entities.LogisticCompany;
import com.jimei.xiaolumeimei.entities.PostActivityBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.entities.StartBean;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import com.jimei.xiaolumeimei.xlmmService.XlmmService;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/19.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ActivityModel {
    private static ActivityModel ourInstance = new ActivityModel();

    private ActivityModel() {
    }

    public static ActivityModel getInstance() {
        return ourInstance;
    }

    //活动内容分享
    public Observable<ActivityBean> get_party_share_content(String id) {
        return XlmmRetrofitClient.getService()
                .get_party_share_content(id)
                .compose(new DefaultTransform<>());
    }

    public Observable<BrandListBean> getBrandList(String id) {
        return XlmmRetrofitClient.getService()
                .getBrandList(id)
                .compose(new DefaultTransform<>());
    }

    //活动海报
    public Observable<List<PostActivityBean>> getPostActivity() {
        return XlmmRetrofitClient.getService()
                .getPostActivity()
                .compose(new DefaultTransform<>());
    }

    //物流列表
    public Observable<List<LogisticCompany>> getLogisticCompany( int referal_trade_id) {
        return XlmmRetrofitClient.getService()
                .getLogisticCompany(referal_trade_id)
                .compose(new DefaultTransform<>());
    }

    //修改物流
    public Observable<ResultBean> changeLogisticCompany(int address_id, String referal_trade_id, String logistic_company_code) {
        return XlmmRetrofitClient.getService()
                .changeLogisticCompany(address_id, referal_trade_id, logistic_company_code)
                .compose(new DefaultTransform<>());
    }

    //领取优惠券
    public Observable<ResponseBody> getUsercoupons(String template_id) {
        return XlmmRetrofitClient.getService()
                .getUsercoupons(template_id)
                .compose(new DefaultTransform<>());
    }

    //获得启动页面数据
    public Observable<StartBean> getStarsBean() {
        return XlmmRetrofitClient.getService()
                .getStarsBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<VersionBean> getVersion(){
        return XlmmRetrofitClient.getService()
                .getVersion()
                .compose(new DefaultTransform<>());
    }
}
