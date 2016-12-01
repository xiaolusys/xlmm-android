package com.jimei.xiaolumeimei.ui.xlmmmain;

import android.support.v4.widget.SwipeRefreshLayout;

import com.jimei.xiaolumeimei.base.BasePresenter;
import com.jimei.xiaolumeimei.base.BaseView;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.GetCoupon;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.VersionBean;

import rx.Observable;

/**
 * Created by itxuye on 2016/7/4.
 */
public interface MainContract {
    interface Model {

        Observable<UserInfoBean> getProfile();

        Observable<GetCoupon> isCouPon();

        Observable<CartsNumResultBean> getCartsNum();

        Observable<PortalBean> getPortalBean();

        Observable<AddressDownloadResultBean> getAddressVersionAndUrl();

        Observable<VersionBean> getVersion();

        Observable<CategoryDownBean> getCategoryDown();

        Observable<UserTopic> getTopic();
    }

    interface View extends BaseView {
        void showBadge();

        void initViewsForTab();

        void initUserView(UserInfoBean userNewBean);

        void initUserViewChange(UserInfoBean userNewBean);

        void initDrawer(UserInfoBean userInfoBean);

        void initShowCoiuponWindow(GetCoupon getCoupon);

        void initMainView(SwipeRefreshLayout swipeRefreshLayout);

        void initSliderLayout(PortalBean postBean);

        void initCategory(PortalBean postBean);

        void initBrand(PortalBean postBean);

        void initActivity(PortalBean postBean);

        void iniCartsNum(CartsNumResultBean cartsNumResultBean);

        void downLoaAddressFile(AddressDownloadResultBean addressDownloadResultBean);

        void downCategoryFile(CategoryDownBean categroyDownBean);

        void checkVersion(int versionCode, String content, String downloadUrl, boolean isAutoUpdate);

        void setTopic(UserTopic userTopic);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getUserInfoBean();

        public abstract void getUserInfoBeanChange();

        public abstract void isCouPon();

        public abstract void getCartsNum();

        public abstract void getPortalBean(SwipeRefreshLayout swipeRefreshLayout);

        public abstract void getAddressVersionAndUrl();

        public abstract void getCategoryDown();

        public abstract void getVersion();

        public abstract void getTopic();
    }
}
