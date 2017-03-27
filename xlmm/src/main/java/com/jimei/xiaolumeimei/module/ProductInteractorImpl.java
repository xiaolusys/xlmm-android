package com.jimei.xiaolumeimei.module;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.SearchHistoryBean;
import com.jimei.xiaolumeimei.entities.ShareModelBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.service.api.ProductService;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by wisdom on 17/2/24.
 */

public class ProductInteractorImpl implements ProductInteractor {
    private final ProductService service;

    @Inject
    public ProductInteractorImpl(ProductService service) {
        this.service = service;
    }

    @Override
    public Subscription getProductDetail(int id, ServiceResponse<ProductDetailBean> response) {
        return service.getProductDetail(id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getShareModel(int model_id, ServiceResponse<ShareModelBean> response) {
        return service.getShareModel(model_id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getCategoryProductList(String cid, int page, String order_by, ServiceResponse<ProductListBean> response) {
        if (order_by != null && !"".equals(order_by)) {
            return service.getCategoryProductList(cid, page, order_by)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        } else {
            return service.getCategoryProductList(cid, page)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        }
    }

    @Override
    public Subscription searchProduct(String name, int page, ServiceResponse<ProductListBean> response) {
        return service.searchProduct(name, page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getSearchHistory(ServiceResponse<SearchHistoryBean> response) {
        return service.getSearchHistory()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription clearSearch(ServiceResponse<Object> response) {
        return service.clearSearch("ModelProduct")
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }
}
