package com.jimei.xiaolumeimei.ui.xlmmmain;

import android.support.v4.widget.SwipeRefreshLayout;
import com.jimei.xiaolumeimei.base.BaseModel;
import com.jimei.xiaolumeimei.base.BasePresenter;
import com.jimei.xiaolumeimei.base.BaseView;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.IsGetcoupon;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.ProductListOldBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.VersionBean;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by itxuye on 2016/7/4.
 */
public interface MainContract {
  interface Model extends BaseModel {

    Observable<Response<UserInfoBean>> getProfile();

    Observable<Response<IsGetcoupon>> isCouPon();

    Observable<CartsNumResultBean> getCartsNum();

    Observable<PortalBean> getPortalBean();

    Observable<ProductListOldBean> getTodayList(int page, int page_size);

    Observable<ResponseBody> getUsercoupons(String template_id);

    Observable<AddressDownloadResultBean> getAddressVersionAndUrl();

    Observable<VersionBean> getVersion();

    Observable<CategoryDownBean> getCategoryDown();
  }

  interface View extends BaseView {
    void findById();

    void initSlide();

    void showBadge();

    void initViewsForTab();

    void initUserView(UserInfoBean userNewBean);

    void initUserViewChange(UserInfoBean userNewBean);

    void initDrawer(UserInfoBean userInfoBean);

    void initShowCoiuponWindow(Response<IsGetcoupon> isGetcouponResponse);

    void initMainView(SwipeRefreshLayout swipeRefreshLayout);

    void initSliderLayout(PortalBean postBean);

    void initCategory(PortalBean postBean);

    void initBrand(PortalBean postBean);

    void initPost(PortalBean postBean);

    void iniCartsNum(CartsNumResultBean cartsNumResultBean);

    void clickGetCounpon(ResponseBody responseBody);

    void downLoaAddressFile(AddressDownloadResultBean addressDownloadResultBean);

    void downCategoryFile(CategoryDownBean categroyDownBean);

    void checkVersion(int versionCode, String content, String downloadUrl, boolean isAutoUpdate);
  }

  abstract class Presenter extends BasePresenter<Model, View> {
    public abstract void getUserInfoBean();

    public abstract void getUserInfoBeanChange();

    public abstract void isCouPon();

    public abstract void getCartsNum();

    public abstract void getPortalBean(SwipeRefreshLayout swipeRefreshLayout);

    public abstract void getTodayList(int page, int page_size);

    public abstract void getUsercoupons(String template_id);

    public abstract long calcLefttowTime(long crtTime);

    public abstract void getAddressVersionAndUrl();

    public abstract void getCategoryDown();

    @Override public void onStart() {
    }

    public abstract void getVersion();
  }
}
