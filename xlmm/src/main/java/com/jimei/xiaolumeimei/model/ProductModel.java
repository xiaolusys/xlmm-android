package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.ActivityGoodListBean;
import com.jimei.xiaolumeimei.entities.BrandListBean;
import com.jimei.xiaolumeimei.entities.CollectionAllBean;
import com.jimei.xiaolumeimei.entities.CollectionDeleteBody;
import com.jimei.xiaolumeimei.entities.CollectionResultBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.SearchHistoryBean;
import com.jimei.xiaolumeimei.entities.ShareModelBean;
import com.jimei.xiaolumeimei.xlmmService.RetrofitClient;
import com.jimei.xiaolumeimei.xlmmService.api.ProductService;

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

    public Observable<ActivityGoodListBean> getActivityList(int promotion_id) {
        return getService()
                .getActivityGoodList(promotion_id)
                .compose(new DefaultTransform<>());
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

    //获得收藏列表
    public Observable<CollectionAllBean> getCollection(int page, String shelf_status) {
        return getService()
                .getCollection(page, shelf_status)
                .compose(new DefaultTransform<>());
    }

    //添加单品收藏
    public Observable<CollectionResultBean> addCollection(int model_id) {
        return getService()
                .addCollection(model_id)
                .compose(new DefaultTransform<>());
    }

    //删除单品收藏
    public Observable<CollectionResultBean> deleteCollection(int model_id) {
        return getService()
                .deleteCollection(new CollectionDeleteBody(model_id))
                .compose(new DefaultTransform<>());
    }

    //根据分类查询商品列表
    public Observable<ProductListBean> getCategoryProductList(String cid, int page, String order_by) {
        return getService()
                .getCategoryProductList(cid, page, order_by)
                .compose(new DefaultTransform<>());
    }

    public Observable<ProductListBean> getBoutiqueList(int page, String order_by) {
        return getService()
                .getBoutiqueList(page, order_by)
                .compose(new DefaultTransform<>());
    }

    //今日上新列表
    private Observable<ProductListBean> getTodayProducts(int page) {
        return getService()
                .getTodayProducts(page)
                .compose(new DefaultTransform<>());
    }

    //即将上新列表
    private Observable<ProductListBean> getTomorrowProducts(int page) {
        return getService()
                .getTomorrowProducts(page)
                .compose(new DefaultTransform<>());
    }

    //昨日热卖列表
    private Observable<ProductListBean> getYesterdayProducts(int page) {
        return getService()
                .getYesterdayProducts(page)
                .compose(new DefaultTransform<>());
    }

    //根据type(昨今明)获取商品列表
    public Observable<ProductListBean> getProductListBean(int page, int type) {
        if (type == XlmmConst.TYPE_YESTERDAY) {
            return getYesterdayProducts(page);
        } else if (type == XlmmConst.TYPE_TODAY) {
            return getTodayProducts(page);
        } else {
            return getTomorrowProducts(page);
        }
    }

    //品牌推广列表
    public Observable<BrandListBean> getBrandList(String id) {
        return getService()
                .getBrandList(id)
                .compose(new DefaultTransform<>());
    }

    //根据关键字查询商品
    public Observable<ProductListBean> searchProduct(String name,int page){
        return getService()
                .searchProduct(name,page)
                .compose(new DefaultTransform<>());
    }

    //获取搜索历史
    public Observable<SearchHistoryBean> getSearchHistory(){
        return getService()
                .getSearchHistory()
                .compose(new DefaultTransform<>());
    }

    //清除搜索历史
    public Observable<Object> clearSearch(){
        return getService()
                .clearSearch("ModelProduct")
                .compose(new DefaultTransform<>());
    }
}
