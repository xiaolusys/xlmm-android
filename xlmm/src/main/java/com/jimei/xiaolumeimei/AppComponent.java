package com.jimei.xiaolumeimei;

/**
 * Created by wisdom on 17/2/23.
 */

import com.jimei.xiaolumeimei.module.ActivityInteractor;
import com.jimei.xiaolumeimei.module.AddressInteractor;
import com.jimei.xiaolumeimei.module.CartsInteractor;
import com.jimei.xiaolumeimei.module.MainInteractor;
import com.jimei.xiaolumeimei.module.ProductInteractor;
import com.jimei.xiaolumeimei.module.TradeInteractor;
import com.jimei.xiaolumeimei.module.UserInteractor;
import com.jimei.xiaolumeimei.module.VipInteractor;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
//    void inject(XlmmApp app);

    ActivityInteractor getActivityInteractor();

    MainInteractor getMainInteractor();

    ProductInteractor getProductInteractor();

    AddressInteractor getAddressInteractor();

    CartsInteractor getCartsInteractor();

    UserInteractor getUserInteractor();

    VipInteractor getVipInteractor();

    TradeInteractor getTradeInteractor();
}