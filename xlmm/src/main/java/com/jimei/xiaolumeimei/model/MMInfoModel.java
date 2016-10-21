package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.MiPushOrderCarryBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by itxuye on 2016/6/24.
 */
public class MMInfoModel {
    private static MMInfoModel ourInstance = new MMInfoModel();

    private MMInfoModel() {
    }

    public static MMInfoModel getInstance() {
        return ourInstance;
    }

    public Observable<MMShoppingBean> getShareShopping() {
        return XlmmRetrofitClient.getService().getShareShopping().compose(new DefaultTransform<>());
    }

    public Observable<MamaFortune> getMamaFortune() {
        return XlmmRetrofitClient.getService().getMamaFortune().compose(new DefaultTransform<>());
    }

    //test
    public Observable<MamaUrl> getMamaUrl() {
        return XlmmRetrofitClient.getService()
                .getMamaUrl("1.0")
                .compose(new DefaultTransform<>());
    }

    public Observable<Response<MamaSelfListBean>> getMaMaselfList() {
        return XlmmRetrofitClient.getService()
                .getMaMaselfList()
                .compose(new DefaultTransform<>());
    }

    public Observable<UserInfoBean> getUserInfo() {
        return XlmmRetrofitClient.getService()
                .getUserInfo()
                .compose(new DefaultTransform<>());
    }

    public Observable<List<MiPushOrderCarryBean>> getLatestOrderCarry() {
        return XlmmRetrofitClient.getService()
                .getLatestOrderCarry()
                .compose(new DefaultTransform<>());
    }

    public Observable<RecentCarryBean> getRecentCarry(String from, String days) {
        return XlmmRetrofitClient.getService()
                .getRecentCarry(from, days)
                .compose(new DefaultTransform<>());
    }
}
