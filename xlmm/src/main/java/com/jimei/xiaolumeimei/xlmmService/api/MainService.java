package com.jimei.xiaolumeimei.xlmmService.api;

import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.IsGetcoupon;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.VersionBean;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by wisdom on 16/11/23.
 */

public interface MainService {

    //获取用户信息
    @GET("/rest/v1/users/profile")
    Observable<Response<UserInfoBean>> getUserLoginInfo();

    @GET("/rest/v1/usercoupons/is_picked_register_gift_coupon")
    Observable<Response<IsGetcoupon>> isCouPon();

    @GET("/rest/v2/carts/show_carts_num")
    Observable<CartsNumResultBean> show_carts_num();

    @GET("/rest/v1/portal")
    Observable<PortalBean> getPortalBean();

    @GET("/rest/v1/districts/latest_version")
    Observable<AddressDownloadResultBean> getAddressVersionAndUrl();

    @GET("/sale/apprelease/newversion")
    Observable<VersionBean> getVersion();

    @GET("/rest/v2/categorys/latest_version")
    Observable<CategoryDownBean> getCategoryDown();

    @GET("/rest/v1/push/topic")
    Observable<UserTopic> getTopic();
}
