package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CategoryProductListBean;
import com.jimei.xiaolumeimei.entities.CollectionAllBean;
import com.jimei.xiaolumeimei.entities.CollectionDeleteBody;
import com.jimei.xiaolumeimei.entities.CollectionResultBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
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

    public Observable<CategoryProductListBean> getCategoryProductList(String cid, int page,String order_by) {
        return XlmmRetrofitClient.getService()
                .getCategoryProductList(cid, page,order_by)
                .compose(new DefaultTransform<>());
    }

    public Observable<ProductListBean> getTodayProducts(int page) {
        return XlmmRetrofitClient.getService()
                .getTodayProducts(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<ProductListBean> getTomorrowProducts(int page) {
        return XlmmRetrofitClient.getService()
                .getTomorrowProducts(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<ProductListBean> getYesterdayProducts(int page) {
        return XlmmRetrofitClient.getService()
                .getYesterdayProducts(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<ProductListBean> getProductListBean(int page, int type) {
        if (type == XlmmConst.TYPE_YESTERDAY) {
            return getYesterdayProducts(page);
        } else if (type == XlmmConst.TYPE_TODAY) {
            return getTodayProducts(page);
        } else {
            return getTomorrowProducts(page);
        }
    }
}
