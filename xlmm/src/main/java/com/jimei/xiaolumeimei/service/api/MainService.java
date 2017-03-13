package com.jimei.xiaolumeimei.service.api;

import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.MainTodayBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.VersionBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wisdom on 16/11/23.
 */

public interface MainService {

    //获取用户信息
    @GET("/rest/v1/users/profile")
    Observable<UserInfoBean> getProfile();

    @GET("/rest/v2/carts/show_carts_num")
    Observable<CartsNumResultBean> getCartsNum(
        @Query("type") int type);

    @GET("/rest/v1/portal")
    Observable<PortalBean> getPortalBean(
        @Query("exclude_fields") String exclude_fields);

    @GET("/rest/v1/districts/latest_version")
    Observable<AddressDownloadResultBean> getAddressVersionAndUrl();

    @GET("/sale/apprelease/newversion")
    Observable<VersionBean> getVersion();

    @GET("/rest/v2/categorys/latest_version")
    Observable<CategoryDownBean> getCategoryDown();

    @GET("/rest/v1/push/topic")
    Observable<UserTopic> getTopic();

    @GET("/rest/v1/pmt/ninepic/today")
    Observable<List<MainTodayBean>> getMainTodayList();
}
