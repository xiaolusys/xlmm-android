package com.jimei.xiaolumeimei.module;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.CartsHisBean;
import com.jimei.xiaolumeimei.entities.CartsInfoBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.service.api.CartsService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscription;

/**
 * Created by wisdom on 17/2/28.
 */

public class CartsInteractorImpl implements CartsInteractor {
    private final CartsService service;

    @Inject
    public CartsInteractorImpl(CartsService service) {
        this.service = service;
    }

    @Override
    public Subscription getCartsList(ServiceResponse<List<CartsInfoBean>> response) {
        return service.getCartsList()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getCartsList(int type, ServiceResponse<List<CartsInfoBean>> response) {
        return service.getCartsList(type)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getCartsHisList(ServiceResponse<List<CartsInfoBean>> response) {
        return service.getCartsHisList()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getCartsPayInfoList(String cart_ids, ServiceResponse<CartsPayinfoBean> response) {
        return service.getCartsPayInfoList(cart_ids)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getCartsPayInfoListV2(String cart_ids, ServiceResponse<CartsPayinfoBean> response) {
        return service.getCartsPayInfoListV2(cart_ids, "app")
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription plusProductCarts(String id, ServiceResponse<Response<CodeBean>> response) {
        return service.plus_product_carts(id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription minusProductCarts(String id, ServiceResponse<Response<CodeBean>> response) {
        return service.minus_product_carts(id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription deleteCarts(String id, ServiceResponse<Response<CodeBean>> response) {
        return service.delete_carts(id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription rebuy(String item_id, String sku_id, String cart_id, ServiceResponse<CartsHisBean> response) {
        return service.rebuy(item_id, sku_id, cart_id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription addToCart(int item_id, int sku_id, int num, ServiceResponse<ResultEntity> response) {
        return service.addToCart(item_id, sku_id, num)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription addToCart(int item_id, int sku_id, int num, int type, ServiceResponse<ResultEntity> response) {
        return service.addToCart(item_id, sku_id, num, type)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }
}
