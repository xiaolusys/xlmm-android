package com.jimei.xiaolumeimei.module;

import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.MainTodayBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.List;

import rx.Subscription;

/**
 * Created by wisdom on 17/2/24.
 */

public interface MainInteractor {
    Subscription getProfile(ServiceResponse<UserInfoBean> serviceResponse);

    Subscription getCartsNum(ServiceResponse<CartsNumResultBean> serviceResponse);

    Subscription getPortalBean(String exclude_fields, ServiceResponse<PortalBean> serviceResponse);

    Subscription getAddressVersionAndUrl(ServiceResponse<AddressDownloadResultBean> serviceResponse);

    Subscription getVersion(ServiceResponse<VersionBean> serviceResponse);

    Subscription getCategoryDown(ServiceResponse<CategoryDownBean> serviceResponse);

    Subscription getTopic(ServiceResponse<UserTopic> serviceResponse);

    Subscription getMainTodayList(ServiceResponse<List<MainTodayBean>> serviceResponse);
}
