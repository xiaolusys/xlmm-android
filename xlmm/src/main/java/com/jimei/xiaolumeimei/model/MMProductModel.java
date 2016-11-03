package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.entities.ClickcarryBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.SaveTimeBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;

import java.util.List;

import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/14.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMProductModel {

    private static MMProductModel ourInstance = new MMProductModel();

    private MMProductModel() {
    }

    public static MMProductModel getInstance() {
        return ourInstance;
    }

    public Observable<List<NinePicBean>> getNinePic(int sale_category) {
        if (sale_category == -1) {
            return XlmmRetrofitClient.getService()
                    .getNinePic()
                    .compose(new DefaultTransform<>());
        } else {
            return XlmmRetrofitClient.getService()
                    .getNinePic(sale_category)
                    .compose(new DefaultTransform<>());
        }
    }

    public Observable<List<NinePicBean>> getNinePicByOrdering() {
        return XlmmRetrofitClient.getService()
                .getNinePic("-save_times")
                .compose(new DefaultTransform<>());
    }

    public Observable<MMShoppingBean> getShareShopping() {
        return XlmmRetrofitClient.getService()
                .getShareShopping()
                .compose(new DefaultTransform<>());
    }

    //得到全部历史收益
    public Observable<CarryLogListBean> getMamaAllCarryLogs(String page) {
        return XlmmRetrofitClient.getService()
                .getMamaAllCarryLogs(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<OderCarryBean> getMamaAllOderCarryLogs(String page) {
        return XlmmRetrofitClient.getService()
                .getMamaAllOderCarryLogs(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<OderCarryBean> getMamaAllOderCarryLogs( int page) {
        return XlmmRetrofitClient.getService()
                .getMamaAllOderCarryLogs("direct", page)
                .compose(new DefaultTransform<>());
    }

    public Observable<AwardCarryBean> getMamaAllAwardCarryLogs(String page) {
        return XlmmRetrofitClient.getService()
                .getMamaAllAwardCarryLogs(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<ClickcarryBean> getMamaAllClickCarryLogs(String page) {
        return XlmmRetrofitClient.getService()
                .getMamaAllClickCarryLogs(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<WxQrcode> getWxCode() {
        return XlmmRetrofitClient.getService()
                .getWxCode()
                .compose(new DefaultTransform<>());
    }

    public Observable<PortalBean> getPortalBean() {
        return XlmmRetrofitClient.getService()
                .getPortalBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<SaveTimeBean> saveTime(int id, int save_times) {
        return XlmmRetrofitClient.getService()
                .saveTime(id, save_times)
                .compose(new DefaultTransform<>());
    }

}
