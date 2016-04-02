package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.entities.ClickcarryBean;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MMStoreBean;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.entities.OneDayAgentOrdersBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.ShopProductBean;
import com.jimei.xiaolumeimei.entities.ShoppingListBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import com.squareup.okhttp.ResponseBody;

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

    //得到MM店铺列表
    public Observable<List<MMStoreBean>> getMMStoreList() {
        return XlmmRetrofitClient.getService()
                .getMMStoreList()
                .compose(new DefaultTransform<>());
    }

  /*//得到默认选品列表
  public Observable<List<MMChooselistBean>> getMMChooseList() {
    return XlmmRetrofitClient.getService()
        .getMMChooseList()
        .compose(new DefaultTransform<>());
  }

  //得到默认选品列表排序
  public Observable<List<MMChooselistBean>> getMMChooseSortList(String sort_field) {
    return XlmmRetrofitClient.getService()
        .getMMChooseSortList(sort_field)
        .compose(new DefaultTransform<>());
  }

  //选品女装或者童装列表
  public Observable<List<MMChooselistBean>> getMMChooseLadyOrChildList(String category) {
    return XlmmRetrofitClient.getService()
        .getMMChooseLadyOrChildList(category)
        .compose(new DefaultTransform<>());
  }*/

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

    //MM上架商品到商铺
    public Observable<ResponseResultBean> add_pro_to_shop(String product) {
        return XlmmRetrofitClient.getService()
                .add_pro_to_shop(product)
                .compose(new DefaultTransform<>());
    }

    //MM下架商品
    public Observable<ResponseResultBean> remove_pro_from_shop(String product) {
        return XlmmRetrofitClient.getService()
                .remove_pro_from_shop(product)
                .compose(new DefaultTransform<>());
    }

    //MM订单历史
    public Observable<ShoppingListBean> getShoppingList(String page) {
        return XlmmRetrofitClient.getService()
                .getShoppingList(page)
                .compose(new DefaultTransform<>());
    }

    //MM订单历史
    public Observable<CarryLogListBean> getCarryLogList(String page) {
        return XlmmRetrofitClient.getService()
                .getCarrylogList(page)
                .compose(new DefaultTransform<>());
    }

    //MM one day订单
    public Observable<OneDayAgentOrdersBean> getOneDayAgentOrders(int day) {
        return XlmmRetrofitClient.getService()
                .getOneDayAgentOrders(Integer.toString(day))
                .compose(new DefaultTransform<>());
    }

    //MM latest day订单number
    public Observable<List<Integer>> getLatestAgentOrders(int day) {
        return XlmmRetrofitClient.getService()
                .getLatestAgentOrders(Integer.toString(day))
                .compose(new DefaultTransform<>());
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

    //MM recent days carry
    public Observable<RecentCarryBean> getRecentCarry(String from, String day) {
        return XlmmRetrofitClient.getService()
                .getRecentCarry(from, day)
                .compose(new DefaultTransform<>());
    }

    public Observable<ShopProductBean> getShopProduct(String page) {

        return XlmmRetrofitClient.getService().getShopProduct(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<ResponseBody> changeProPosition(String change_id, String target_id) {
        return XlmmRetrofitClient.getService().changeProPosition(change_id, target_id)
                .compose(new DefaultTransform<>());
    }

    public Observable<ResponseBody> removeProFromShop(String id) {
        return XlmmRetrofitClient.getService().removeProFromShop(id)

                .compose(new DefaultTransform<>());
    }

}
