package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CategoryProductListBean;
import com.jimei.xiaolumeimei.entities.CollectionAllBean;
import com.jimei.xiaolumeimei.entities.CollectionDeleteBody;
import com.jimei.xiaolumeimei.entities.CollectionResultBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.ProductListOldBean;
import com.jimei.xiaolumeimei.entities.ProductListResultBean;
import com.jimei.xiaolumeimei.entities.ShareModelBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;

import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductModel {

    private static ProductModel ourInstance = new ProductModel();

    private ProductModel() {
    }

    public static ProductModel getInstance() {
        return ourInstance;
    }


    //得到今日数据
    public Observable<ProductListOldBean> getTodayList(int page, int page_size) {

        return XlmmRetrofitClient.getService()
                .getTodayList(page, page_size)
                .compose(new DefaultTransform<>());
    }

    //得到昨日分页数据
    public Observable<ProductListOldBean> getPreviousList(int page, int page_size) {

        return XlmmRetrofitClient.getService()
                .getPreviousList(page, page_size)
                .compose(new DefaultTransform<>());
    }


    //得到明日分页数据
    public Observable<ProductListOldBean> getAdvanceList(int page, int page_size) {

        return XlmmRetrofitClient.getService()
                .getAdvanceList(page, page_size)
                .compose(new DefaultTransform<>());
    }

    public Observable<ProductListResultBean> getProductList(int page, int page_size, int type) {
        if (type == XlmmConst.TYPE_LADY) {
            return XlmmRetrofitClient.getService()
                    .getLadyList(page, page_size)
                    .compose(new DefaultTransform<>());
        } else {
            return XlmmRetrofitClient.getService()
                    .getChildList(page, page_size)
                    .compose(new DefaultTransform<>());
        }

    }

    //得到分页数据,升价排序
    public Observable<ProductListResultBean> getProductList(int page, int page_size, String order_by, int type) {
        if (type == XlmmConst.TYPE_LADY) {
            return XlmmRetrofitClient.getService()
                    .getLadyList(page, page_size, order_by)
                    .compose(new DefaultTransform<>());
        } else {
            return XlmmRetrofitClient.getService()
                    .getChildList(page, page_size, order_by)
                    .compose(new DefaultTransform<>());
        }
    }

    //得到商品详情(新)
    public Observable<ProductDetailBean> getProductDetail(int id) {

        return XlmmRetrofitClient.getService()
                .getProductDetail(id)
                .compose(new DefaultTransform<>());
    }

    //单肩商品分享数据
    public Observable<ShareModelBean> getShareModel(int model_id) {
        return XlmmRetrofitClient.getService()
                .getShareModel(model_id)
                .compose(new DefaultTransform<>());
    }

    //获得收藏列表
    public Observable<CollectionAllBean> getCollection(int page, String shelf_status) {
        return XlmmRetrofitClient.getService()
                .getCollection(page, shelf_status)
                .compose(new DefaultTransform<>());
    }

    //添加单品收藏
    public Observable<CollectionResultBean> addCollection(int model_id) {
        return XlmmRetrofitClient.getService()
                .addCollection(model_id)
                .compose(new DefaultTransform<>());
    }

    //删除单品收藏
    public Observable<CollectionResultBean> deleteCollection(int model_id) {
        return XlmmRetrofitClient.getService()
                .deleteCollection(new CollectionDeleteBody(model_id))
                .compose(new DefaultTransform<>());
    }

    public Observable<CategoryProductListBean> getCategoryProductList(String cid, int page) {
        return XlmmRetrofitClient.getService()
                .getCategoryProductList(cid, page)
                .compose(new DefaultTransform<>());
    }

    public Observable<ProductListBean> getTodayProducts() {
        return XlmmRetrofitClient.getService()
                .getTodayProducts()
                .compose(new DefaultTransform<>());
    }

    public Observable<ProductListBean> getTomorrowProducts() {
        return XlmmRetrofitClient.getService()
                .getTomorrowProducts()
                .compose(new DefaultTransform<>());
    }

    public Observable<ProductListBean> getYesterdayProducts() {
        return XlmmRetrofitClient.getService()
                .getYesterdayProducts()
                .compose(new DefaultTransform<>());
    }
}
