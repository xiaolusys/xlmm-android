package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.StartBean;
import com.jimei.xiaolumeimei.service.RetrofitClient;
import com.jimei.xiaolumeimei.service.api.ActivityService;

import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/19.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ActivityModel {
    private static ActivityService activityService;

    private static ActivityModel ourInstance = new ActivityModel();

    private ActivityModel() {
    }

    public static ActivityService getService() {
        if (activityService == null) {
            activityService = RetrofitClient.createAdapter().create(ActivityService.class);
        }
        return activityService;
    }

    public static ActivityModel getInstance() {
        return ourInstance;
    }

    //活动内容分享
    public Observable<ActivityBean> get_party_share_content(String id) {
        return getService().get_party_share_content(id)
            .compose(new DefaultTransform<>());
    }

    public Observable<StartBean> getStartAds() {
        return getService().getStartAds()
            .compose(new DefaultTransform<>());
    }
}
