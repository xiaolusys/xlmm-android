package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.MainTodayBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.service.RetrofitClient;
import com.jimei.xiaolumeimei.service.api.MainService;

import java.util.List;

import rx.Observable;

/**
 * Created by itxuye on 2016/7/4.
 */
public class MainModel {
    private static MainService mainService;
    private static MainModel mainModel = new MainModel();

    public static MainModel getInstance() {
        return mainModel;
    }

    private static MainService getService() {
        if (mainService == null) {
            mainService = RetrofitClient.createAdapter().create(MainService.class);
        }
        return mainService;
    }

    public Observable<UserInfoBean> getProfile() {
        return getService()
            .getUserLoginInfo()
            .compose(new DefaultTransform<>());
    }

    public Observable<CartsNumResultBean> getCartsNum() {
        return getService()
            .show_carts_num()
            .compose(new DefaultTransform<>());
    }

    public Observable<PortalBean> getPortalBean() {
        return getService()
            .getPortalBean()
            .compose(new DefaultTransform<>());
    }

    public Observable<PortalBean> getPortalBean(String exclude_fields) {
        return getService()
            .getPortalBean(exclude_fields)
            .compose(new DefaultTransform<>());
    }

    public Observable<AddressDownloadResultBean> getAddressVersionAndUrl() {
        return getService()
            .getAddressVersionAndUrl()
            .compose(new DefaultTransform<>());
    }

    public Observable<VersionBean> getVersion() {
        return getService()
            .getVersion()
            .compose(new DefaultTransform<>());
    }

    public Observable<CategoryDownBean> getCategoryDown() {
        return getService()
            .getCategoryDown()
            .compose(new DefaultTransform<>());
    }

    public Observable<UserTopic> getTopic() {
        return getService()
            .getTopic()
            .compose(new DefaultTransform<>());
    }

    public Observable<List<MainTodayBean>> getMainTodayList() {
        return getService()
            .getMainTodayList()
            .compose(new DefaultTransform<>());
    }

}
