package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.LogisticCompany;
import com.jimei.xiaolumeimei.entities.PostActivityBean;
import com.jimei.xiaolumeimei.entities.StartBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;

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
  /*public Observable<ActivityBean> get_share_content(String ufrom) {
    return XlmmRetrofitClient.getService()
        .get_share_content(ufrom)
        .compose(new DefaultTransform<>());
  }*/

    //活动内容分享
    public Observable<ActivityBean> get_party_share_content(String id) {
        return XlmmRetrofitClient.getService()
                .get_party_share_content(id)
                .compose(new DefaultTransform<>());
    }

    //活动海报
    public Observable<List<PostActivityBean>> getPostActivity() {
        return XlmmRetrofitClient.getService()
                .getPostActivity()
                .compose(new DefaultTransform<>());
    }

    //物流列表
    public Observable<List<LogisticCompany>> getLogisticCompany() {
        return XlmmRetrofitClient.getService()
                .getLogisticCompany()
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
}
