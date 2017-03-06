package com.jimei.xiaolumeimei.module;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.StartBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.service.api.ActivityService;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by wisdom on 17/2/24.
 */

public class ActivityInteractorImpl implements ActivityInteractor {

    private final ActivityService service;

    @Inject
    public ActivityInteractorImpl(ActivityService service) {
        this.service = service;

    }

    @Override
    public Subscription get_party_share_content(String id, ServiceResponse<ActivityBean> response) {
        return service.get_party_share_content(id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getStartAds(ServiceResponse<StartBean> response) {
        return service.getStartAds()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }
}
