package com.jimei.xiaolumeimei.module;

import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.SearchHistoryBean;
import com.jimei.xiaolumeimei.entities.ShareModelBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import rx.Subscription;

/**
 * Created by wisdom on 17/2/24.
 */

public interface ProductInteractor {

    Subscription getProductDetail(int id, ServiceResponse<ProductDetailBean> response);

    Subscription getShareModel(int model_id, ServiceResponse<ShareModelBean> response);

    Subscription getCategoryProductList(String cid, int page, String order_by, ServiceResponse<ProductListBean> response);

    Subscription searchProduct(String name, int page, ServiceResponse<ProductListBean> response);

    Subscription getSearchHistory(ServiceResponse<SearchHistoryBean> response);

    Subscription clearSearch(ServiceResponse<Object> response);
}
