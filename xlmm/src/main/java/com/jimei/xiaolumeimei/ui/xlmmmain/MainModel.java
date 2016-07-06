package com.jimei.xiaolumeimei.ui.xlmmmain;

import com.jimei.xiaolumeimei.base.RxSchedulers;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.IsGetcoupon;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by itxuye on 2016/7/4.
 */
public class MainModel implements MainContract.Model {
  @Override public Observable<UserInfoBean> getProfile() {
    return XlmmRetrofitClient
        .getService()
        .getProfile()
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

  @Override public Observable<ProductListBean> getTodayList(int page,int page_size) {
    return XlmmRetrofitClient
        .getService()
        .getTodayList(page,page_size)
        .compose(RxSchedulers.io_main());
  }

  @Override public Observable<ResponseBody> getUsercoupons(String template_id) {
    return XlmmRetrofitClient
        .getService()
        .getUsercoupons(template_id)
        .compose(RxSchedulers.io_main());
  }
}
