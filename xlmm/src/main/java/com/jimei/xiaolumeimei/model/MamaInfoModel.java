package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.ChooseListBean;
import com.jimei.xiaolumeimei.entities.ClickcarryBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.SaveTimeBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.service.RetrofitClient;
import com.jimei.xiaolumeimei.service.api.MamaService;

import java.util.List;

import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class MamaInfoModel {

    private static MamaInfoModel single_model = new MamaInfoModel();
    private static MamaService mamaService;

    private static MamaService getService() {
        if (mamaService == null) {
            mamaService = RetrofitClient.createAdapter().create(MamaService.class);
        }
        return mamaService;
    }

    private MamaInfoModel() {
    }

    public static MamaInfoModel getInstance() {
        return single_model;
    }

    //得到妈妈粉丝列表
    public Observable<MamaFansBean> getMamaFans(String page) {
        return getService()
            .getMamaFans(page)
            .compose(new DefaultTransform<>());
    }

    //得到妈妈访客列表
    public Observable<MMVisitorsBean> getMamaVisitor(int page) {
        return getService()
            .getMamaVisitor(14, page)
            .compose(new DefaultTransform<>());
    }

    //得到妈妈财富
    public Observable<MamaFortune> getMamaFortune() {
        return getService()
            .getMamaFortune()
            .compose(new DefaultTransform<>());
    }

    public Observable<List<CategoryBean>> getCategory() {
        return getService()
            .getCategory()
            .compose(new DefaultTransform<>());
    }

    public Observable<ChooseListBean> getChooseList(int page, String sort_field, String cid, int reverse) {
        if ("".equals(sort_field) && "".equals(cid)) {
            return getService()
                .getChooseList(page)
                .compose(new DefaultTransform<>());
        } else if ("".equals(sort_field)) {
            return getService()
                .getChooseListByCid(page, cid)
                .compose(new DefaultTransform<>());
        } else if ("".equals(cid)) {
            return getService()
                .getChooseListBySort(page, sort_field, reverse)
                .compose(new DefaultTransform<>());
        } else {
            return getService()
                .getChooseList(page, sort_field, cid, reverse)
                .compose(new DefaultTransform<>());
        }
    }

    public Observable<MMShoppingBean> getShareShopping() {
        return getService()
            .getShareShopping()
            .compose(new DefaultTransform<>());
    }

    //test
    public Observable<MamaUrl> getMamaUrl() {
        return getService()
            .getMamaUrl("1.0")
            .compose(new DefaultTransform<>());
    }

    public Observable<MamaSelfListBean> getMaMaselfList() {
        return getService()
            .getMaMaselfList()
            .compose(new DefaultTransform<>());
    }


    public Observable<RecentCarryBean> getRecentCarry(String from, String days) {
        return getService()
            .getRecentCarry(from, days)
            .compose(new DefaultTransform<>());
    }

    public Observable<List<NinePicBean>> getNinePic(int sale_category) {
        if (sale_category == -1) {
            return getService()
                .getNinePic()
                .compose(new DefaultTransform<>());
        } else {
            return getService()
                .getNinePic(sale_category)
                .compose(new DefaultTransform<>());
        }
    }

    public Observable<List<NinePicBean>> getNinePicByModelId(int model_id) {
        return getService()
            .getNinePicByModelId(model_id)
            .compose(new DefaultTransform<>());
    }

    public Observable<ProductListBean> getBoutiqueList(int page) {
        return getService()
            .getBoutiqueList(page)
            .compose(new DefaultTransform<>());
    }

    public Observable<List<NinePicBean>> getNinePicByOrdering() {
        return getService()
            .getNinePic("-save_times")
            .compose(new DefaultTransform<>());
    }

    //得到全部历史收益
    public Observable<CarryLogListBean> getMamaAllCarryLogs(String page) {
        return getService()
            .getMamaAllCarryLogs(page)
            .compose(new DefaultTransform<>());
    }

    public Observable<OderCarryBean> getMamaAllOderCarryLogs(String page) {
        return getService()
            .getMamaAllOderCarryLogs(page)
            .compose(new DefaultTransform<>());
    }

    public Observable<OderCarryBean> getMamaAllOderCarryLogs(int page) {
        return getService()
            .getMamaAllOderCarryLogs("direct", page)
            .compose(new DefaultTransform<>());
    }

    public Observable<AwardCarryBean> getMamaAllAwardCarryLogs(String page) {
        return getService()
            .getMamaAllAwardCarryLogs(page)
            .compose(new DefaultTransform<>());
    }

    public Observable<ClickcarryBean> getMamaAllClickCarryLogs(String page) {
        return getService()
            .getMamaAllClickCarryLogs(page)
            .compose(new DefaultTransform<>());
    }

    public Observable<WxQrcode> getWxCode() {
        return getService()
            .getWxCode()
            .compose(new DefaultTransform<>());
    }

    public Observable<SaveTimeBean> saveTime(int id, int save_times) {
        return getService()
            .saveTime(id, save_times)
            .compose(new DefaultTransform<>());
    }
}
