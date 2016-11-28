package com.jimei.xiaolumeimei.ui.xlmmmain;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.IsGetcoupon;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.xlmmService.RetrofitClient;
import com.jimei.xiaolumeimei.xlmmService.api.MainService;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by itxuye on 2016/7/4.
 */
public class MainModel implements MainContract.Model {
    private static MainService mainService;

    private static MainService getService() {
        if (mainService == null) {
            mainService = RetrofitClient.createAdapter().create(MainService.class);
        }
        return mainService;
    }

    @Override
    public Observable<Response<UserInfoBean>> getProfile() {
        return getService()
                .getUserLoginInfo()
                .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<Response<IsGetcoupon>> isCouPon() {
        return getService()
                .isCouPon()
                .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<CartsNumResultBean> getCartsNum() {
        return getService()
                .show_carts_num()
                .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<PortalBean> getPortalBean() {
        return getService()
                .getPortalBean()
                .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<AddressDownloadResultBean> getAddressVersionAndUrl() {
        return getService()
                .getAddressVersionAndUrl()
                .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<VersionBean> getVersion() {
        return getService()
                .getVersion()
                .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<CategoryDownBean> getCategoryDown() {
        return getService()
                .getCategoryDown()
                .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<UserTopic> getTopic() {
        return getService()
                .getTopic()
                .compose(new DefaultTransform<>());
    }

}
