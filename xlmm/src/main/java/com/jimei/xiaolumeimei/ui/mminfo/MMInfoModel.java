package com.jimei.xiaolumeimei.ui.mminfo;

import com.jimei.xiaolumeimei.base.RxSchedulers;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MaMaRenwuListBean;
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
public class MMInfoModel implements MMInfoContract.Model {
    private static MMInfoModel ourInstance = new MMInfoModel();

    private MMInfoModel() {
    }

    public static MMInfoModel getInstance() {
        return ourInstance;
    }

    @Override
    public Observable<MMShoppingBean> getShareShopping() {
        return XlmmRetrofitClient.getService().getShareShopping().compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<MamaFortune> getMamaFortune() {
        return XlmmRetrofitClient.getService().getMamaFortune().compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<RecentCarryBean> getRecentCarry(String from, String day) {
        return XlmmRetrofitClient.getService()
                .getRecentCarry(from, day)
                .compose(RxSchedulers.io_main());
    }

    //test
    @Override
    public Observable<MamaUrl> getMamaUrl() {
        return XlmmRetrofitClient.getService()
                .getMamaUrl("1.0")
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<Response<MaMaRenwuListBean>> getMaMaRenwuListBean(String id) {
        return XlmmRetrofitClient.getService()
                .getMaMaRenwuListBean(id)
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<Response<MamaSelfListBean>> getMaMaselfList() {
        return XlmmRetrofitClient.getService()
                .getMaMaselfList()
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<UserInfoBean> getUserInfo() {
        return XlmmRetrofitClient.getService()
                .getUserInfo()
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<List<MiPushOrderCarryBean>> getLatestOrderCarry() {
        return XlmmRetrofitClient.getService()
                .getLatestOrderCarry()
                .compose(RxSchedulers.io_main());
    }
}
