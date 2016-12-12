package com.jimei.xiaolumeimei.widget;

import com.jimei.xiaolumeimei.entities.CartsInfoBean;

import rx.Subscription;

/**
 * Created by wisdom on 16/12/2.
 */

public interface ICartHelper {
    void showIndeterminateProgressDialog(boolean b);

    void addSubscription(Subscription s);

    void addHistory(CartsInfoBean cartsInfoBean);

    void removeCartList(CartsInfoBean cartsInfoBean);

    void removeHistory(CartsInfoBean cartsInfoBean);

    void hideIndeterminateProgressDialog();

    void setPriceText();

    void refreshCartList();

}
