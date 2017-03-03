package com.jimei.xiaolumeimei.module;

import com.jimei.xiaolumeimei.entities.CartsHisBean;
import com.jimei.xiaolumeimei.entities.CartsInfoBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.List;

import retrofit2.Response;
import rx.Subscription;

/**
 * Created by wisdom on 17/2/28.
 */

public interface CartsInteractor {
    Subscription getCartsList(ServiceResponse<List<CartsInfoBean>> response);

    Subscription getCartsList(int type, ServiceResponse<List<CartsInfoBean>> response);

    Subscription getCartsHisList(ServiceResponse<List<CartsInfoBean>> response);

    Subscription getCartsPayInfoList(String cart_ids, ServiceResponse<CartsPayinfoBean> response);

    Subscription getCartsPayInfoListV2(String cart_ids, ServiceResponse<CartsPayinfoBean> response);

    Subscription plusProductCarts(String id, ServiceResponse<Response<CodeBean>> response);

    Subscription minusProductCarts(String id, ServiceResponse<Response<CodeBean>> response);

    Subscription deleteCarts(String id, ServiceResponse<Response<CodeBean>> response);

    Subscription rebuy(String item_id, String sku_id, String cart_id, ServiceResponse<CartsHisBean> response);

    Subscription addToCart(int item_id, int sku_id, int num, ServiceResponse<ResultEntity> response);

    Subscription addToCart(int item_id, int sku_id, int num, int type, ServiceResponse<ResultEntity> response);
}
