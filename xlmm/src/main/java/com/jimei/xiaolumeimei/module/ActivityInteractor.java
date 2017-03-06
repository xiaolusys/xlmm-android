package com.jimei.xiaolumeimei.module;

import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.StartBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import rx.Subscription;

/**
 * Created by wisdom on 17/2/24.
 */

public interface ActivityInteractor {

    Subscription get_party_share_content(String id, ServiceResponse<ActivityBean> response);

    Subscription getStartAds(ServiceResponse<StartBean> response);
}
