package com.jimei.xiaolumeimei.module;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.MainTodayBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.service.api.MainService;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by wisdom on 17/2/24.
 */

public class MainInteractorImpl implements MainInteractor {
    private final MainService service;

    @Inject
    public MainInteractorImpl(MainService service) {
        this.service = service;
    }

    @Override
    public Subscription getProfile(ServiceResponse<UserInfoBean> serviceResponse) {
        return service.getProfile()
            .compose(new DefaultTransform<>())
            .subscribe(serviceResponse);
    }

    @Override
    public Subscription getCartsNum(ServiceResponse<CartsNumResultBean> serviceResponse) {
        return service.getCartsNum()
            .compose(new DefaultTransform<>())
            .subscribe(serviceResponse);
    }

    @Override
    public Subscription getPortalBean(String exclude_fields, ServiceResponse<PortalBean> serviceResponse) {
        return service.getPortalBean(exclude_fields)
            .compose(new DefaultTransform<>())
            .subscribe(serviceResponse);
    }

    @Override
    public Subscription getAddressVersionAndUrl(ServiceResponse<AddressDownloadResultBean> serviceResponse) {
        return service.getAddressVersionAndUrl()
            .compose(new DefaultTransform<>())
            .subscribe(serviceResponse);
    }

    @Override
    public Subscription getVersion(ServiceResponse<VersionBean> serviceResponse) {
        return service.getVersion()
            .compose(new DefaultTransform<>())
            .subscribe(serviceResponse);
    }

    @Override
    public Subscription getCategoryDown(ServiceResponse<CategoryDownBean> serviceResponse) {
        return service.getCategoryDown()
            .compose(new DefaultTransform<>())
            .subscribe(serviceResponse);
    }

    @Override
    public Subscription getTopic(ServiceResponse<UserTopic> serviceResponse) {
        return service.getTopic()
            .compose(new DefaultTransform<>())
            .subscribe(serviceResponse);
    }

    @Override
    public Subscription getMainTodayList(ServiceResponse<List<MainTodayBean>> serviceResponse) {
        return service.getMainTodayList()
            .compose(new DefaultTransform<>())
            .subscribe(serviceResponse);
    }
}
