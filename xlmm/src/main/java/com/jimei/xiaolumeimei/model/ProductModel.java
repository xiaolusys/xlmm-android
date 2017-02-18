package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.SearchHistoryBean;
import com.jimei.xiaolumeimei.entities.ShareModelBean;
import com.jimei.xiaolumeimei.service.RetrofitClient;
import com.jimei.xiaolumeimei.service.api.ProductService;

import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductModel {

    private static ProductModel ourInstance = new ProductModel();
    private static ProductService productService;

    private static ProductService getService() {
        if (productService == null) {
            productService = RetrofitClient.createAdapter().create(ProductService.class);
        }
        return productService;
    }

    private ProductModel() {
    }

    public static ProductModel getInstance() {
        return ourInstance;
    }

    //得到商品详情(新)
    public Observable<ProductDetailBean> getProductDetail(int id) {
        return getService()
            .getProductDetail(id)
            .compose(new DefaultTransform<>());
    }

    //单肩商品分享数据
    public Observable<ShareModelBean> getShareModel(int model_id) {
        return getService()
            .getShareModel(model_id)
            .compose(new DefaultTransform<>());
    }

    //根据分类查询商品列表
    public Observable<ProductListBean> getCategoryProductList(String cid, int page, String order_by) {
        return getService()
            .getCategoryProductList(cid, page, order_by)
            .compose(new DefaultTransform<>());
    }

    //根据分类查询商品列表
    public Observable<ProductListBean> getCategoryProductList(int cid, int page) {
        return getService()
            .getCategoryProductList(cid, page)
            .compose(new DefaultTransform<>());
    }

    //根据关键字查询商品
    public Observable<ProductListBean> searchProduct(String name, int page) {
        return getService()
            .searchProduct(name, page)
            .compose(new DefaultTransform<>());
    }

    //获取搜索历史
    public Observable<SearchHistoryBean> getSearchHistory() {
        return getService()
            .getSearchHistory()
            .compose(new DefaultTransform<>());
    }

    //清除搜索历史
    public Observable<Object> clearSearch() {
        return getService()
            .clearSearch("ModelProduct")
            .compose(new DefaultTransform<>());
    }
}
