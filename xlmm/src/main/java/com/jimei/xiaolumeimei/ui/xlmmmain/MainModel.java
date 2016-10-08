package com.jimei.xiaolumeimei.ui.xlmmmain;

import com.jimei.xiaolumeimei.base.RxSchedulers;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.IsGetcoupon;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by itxuye on 2016/7/4.
 */
public class MainModel implements MainContract.Model {
  @Override public Observable<Response<UserInfoBean>> getProfile() {
    return XlmmRetrofitClient
        .getService()
        .getUserLoginInfo()
        .compose(RxSchedulers.io_main());
  }

  @Override public Observable<Response<IsGetcoupon>> isCouPon() {
    return XlmmRetrofitClient
        .getService()
        .isCouPon()
        .compose(RxSchedulers.io_main());
  }

  @Override public Observable<CartsNumResultBean> getCartsNum() {
    return XlmmRetrofitClient
        .getService()
        .show_carts_num()
        .compose(RxSchedulers.io_main());
  }

  @Override public Observable<PortalBean> getPortalBean() {
    return XlmmRetrofitClient
        .getService()
        .getPortalBean()
        .compose(RxSchedulers.io_main());
  }

  @Override public Observable<AddressDownloadResultBean> getAddressVersionAndUrl() {
    return XlmmRetrofitClient
        .getService()
        .getAddressVersionAndUrl()
        .compose(RxSchedulers.io_main());
  }

  @Override
  public Observable<VersionBean> getVersion() {
    return XlmmRetrofitClient
            .getService()
            .getVersion()
            .compose(RxSchedulers.io_main());
  }

  @Override
  public Observable<CategoryDownBean> getCategoryDown() {
    return XlmmRetrofitClient
            .getService()
            .getCategoryDown()
            .compose(RxSchedulers.io_main());
  }

  @Override
  public Observable<UserTopic> getTopic() {
    return XlmmRetrofitClient
            .getService()
            .getTopic()
            .compose(RxSchedulers.io_main());
  }

}
