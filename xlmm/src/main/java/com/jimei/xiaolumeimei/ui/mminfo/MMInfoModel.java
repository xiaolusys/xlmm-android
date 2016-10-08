package com.jimei.xiaolumeimei.ui.mminfo;

import com.jimei.xiaolumeimei.base.RxSchedulers;
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
        return XlmmRetrofitClient.getService().getShareShopping().compose(RxSchedulers.io_main());
    }

    public Observable<MamaFortune> getMamaFortune() {
        return XlmmRetrofitClient.getService().getMamaFortune().compose(RxSchedulers.io_main());
    }

    //test
    public Observable<MamaUrl> getMamaUrl() {
        return XlmmRetrofitClient.getService()
                .getMamaUrl("1.0")
                .compose(RxSchedulers.io_main());
    }

    public Observable<Response<MamaSelfListBean>> getMaMaselfList() {
        return XlmmRetrofitClient.getService()
                .getMaMaselfList()
                .compose(RxSchedulers.io_main());
    }

    Observable<UserInfoBean> getUserInfo() {
        return XlmmRetrofitClient.getService()
                .getUserInfo()
                .compose(RxSchedulers.io_main());
    }

    Observable<List<MiPushOrderCarryBean>> getLatestOrderCarry() {
        return XlmmRetrofitClient.getService()
                .getLatestOrderCarry()
                .compose(RxSchedulers.io_main());
    }

    public Observable<RecentCarryBean> getRecentCarry(String from,String days){
        return XlmmRetrofitClient.getService()
                .getRecentCarry(from,days)
                .compose(RxSchedulers.io_main());
    }
}
