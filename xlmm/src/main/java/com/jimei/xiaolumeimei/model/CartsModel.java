package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.CartsHisBean;
import com.jimei.xiaolumeimei.entities.CartsInfoBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.xlmmService.RetrofitClient;
import com.jimei.xiaolumeimei.xlmmService.api.CartsService;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by 优尼世界 on 2016/01/15.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsModel {

    private static CartsModel ourInstance = new CartsModel();
    private static CartsService cartsService;

    private static CartsService getService() {
        if (cartsService == null) {
            cartsService = RetrofitClient.createAdapter().create(CartsService.class);
        }
        return cartsService;
    }

    private CartsModel() {
    }

    public static CartsModel getInstance() {
        return ourInstance;
    }

    //获取购物车列表信息
    public Observable<List<CartsInfoBean>> getCartsList() {
        return getService()
                .getCartsList()
                .compose(new DefaultTransform<>());
    }

    public Observable<List<CartsInfoBean>> getCartsList(int type) {
        return getService()
                .getCartsList(type)
                .compose(new DefaultTransform<>());
    }

    //获取历史购物车列表信息
    public Observable<List<CartsInfoBean>> getCartsHisList() {
        return getService()
                .getCartsHisList()
                .compose(new DefaultTransform<>());
    }

    //获取购物车列表信息
    public Observable<CartsPayinfoBean> getCartsInfoList(String cart_ids) {
        return getService()
                .getCartsPayInfoList(cart_ids)
                .compose(new DefaultTransform<>());
    }

    //获取购物车列表信息
    public Observable<CartsPayinfoBean> getCartsPayInfoListV2(String cart_ids) {
        return getService()
                .getCartsPayInfoListV2(cart_ids, "app")
                .compose(new DefaultTransform<>());
    }

    //增加一件
    public Observable<Response<CodeBean>> plus_product_carts(String id) {
        return getService()
                .plus_product_carts(id)
                .compose(new DefaultTransform<>());
    }

    //删除一件
    public Observable<Response<CodeBean>> minus_product_carts(String id) {
        return getService()
                .minus_product_carts(id)
                .compose(new DefaultTransform<>());
    }

    //删除一列
    public Observable<Response<CodeBean>> delete_carts(String id) {
        return getService()
                .delete_carts(id)
                .compose(new DefaultTransform<>());
    }

    //显示购物车数量
    public Observable<CartsNumResultBean> show_carts_num() {
        return getService()
                .show_carts_num()
                .compose(new DefaultTransform<>());
    }

    //显示购物车数量
    public Observable<CartsHisBean> rebuy(String item_id, String sku_id, String cart_id) {
        return getService()
                .rebuy(item_id, sku_id, cart_id)
                .compose(new DefaultTransform<>());
    }

    public Observable<ResultEntity> addToCart(int item_id, int sku_id, int num) {
        return getService()
                .addToCart(item_id, sku_id, num)
                .compose(new DefaultTransform<>());
    }

    public Observable<ResultEntity> addToCart(int item_id, int sku_id, int num, int type) {
        return getService()
                .addToCart(item_id, sku_id, num, type)
                .compose(new DefaultTransform<>());
    }
}
