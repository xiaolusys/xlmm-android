package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.entities.ClickcarryBean;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.entities.MMHavaChooseResultBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.entities.ShopProductBean;
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

    //选品女装或者童装列表排序
    public Observable<MMChooselistBean> getMMChooseLadyOrChildSortListSort(
            String sort_field, String category, String page, String pagesize) {
        if ((sort_field == null) || (sort_field.isEmpty())) {
            if ((category == null) || (category.isEmpty())) {
                return XlmmRetrofitClient.getService()
                        .getMMChooseList(page, pagesize)
                        .compose(new DefaultTransform<>());
            } else {
                return XlmmRetrofitClient.getService()
                        .getMMChooseLadyOrChildList(category, page, pagesize)
                        .compose(new DefaultTransform<>());
            }
        } else {
            if ((category == null) || (category.isEmpty())) {
                return XlmmRetrofitClient.getService()
                        .getMMChooseSortList(sort_field, page, pagesize)
                        .compose(new DefaultTransform<>());
            } else {
                return XlmmRetrofitClient.getService()
                        .getMMChooseLadyOrChildSortListSort(sort_field, category, page, pagesize)
                        .compose(new DefaultTransform<>());
            }
        }
    }

    public Observable<List<NinePicBean>> getNinePic() {
        return XlmmRetrofitClient.getService().getNinepic().compose(new DefaultTransform<>());
    }

    public Observable<MMShoppingBean> getShareShopping() {
        return XlmmRetrofitClient.getService().getShareShopping();
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

    public Observable<OderCarryBean> getMamaAllOderCarryLogs(String carry_type,
                                                             String page) {
        return XlmmRetrofitClient.getService()
                .getMamaAllOderCarryLogs(carry_type, page)
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

    public Observable<ShopProductBean> getShopProduct(String page) {

        return XlmmRetrofitClient.getService().getShopProduct(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<MMHavaChooseResultBean> changeProPosition(String change_id, String target_id) {
        return XlmmRetrofitClient.getService().changeProPosition(change_id, target_id)
                .compose(new DefaultTransform<>());
    }

    public Observable<MMHavaChooseResultBean> removeProFromShop(String id) {
        return XlmmRetrofitClient.getService().removeProFromShop(id)
                .compose(new DefaultTransform<>());
    }

}
