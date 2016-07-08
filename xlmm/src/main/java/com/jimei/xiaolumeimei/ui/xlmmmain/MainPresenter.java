package com.jimei.xiaolumeimei.ui.xlmmmain;

import android.support.v4.widget.SwipeRefreshLayout;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.Date;

/**
 * Created by itxuye on 2016/7/4.
 */
public class MainPresenter extends MainContract.Presenter {
  protected UserInfoBean userInfoNewBean;

  @Override public void getUserInfoBean() {
    mRxManager.add(mModel.getProfile().subscribe(userInfoBean -> {
      if (null != userInfoBean) {
        userInfoNewBean = userInfoBean;
        mView.initDrawer(userInfoBean);
        mView.initUserView(userInfoBean);
      }
    }, Throwable::printStackTrace));
  }

  @Override public void getUserInfoBeanFromLogin() {
    mRxManager.add(mModel.getProfile().subscribe(new ServiceResponse<UserInfoBean>(){
      @Override public void onNext(UserInfoBean userInfoBean) {
        if (null != userInfoBean) {
          userInfoNewBean = userInfoBean;
          mView.initDrawer(userInfoBean);
          mView.initUserView(userInfoBean);
        }
      }

      @Override public void onCompleted() {
        super.onCompleted();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
      }
    }));
  }

  @Override public void isCouPon() {
    mRxManager.add(mModel.isCouPon().subscribe(isGetcouponResponse -> {
      if (null != isGetcouponResponse) {
        mView.initShowCoiuponWindow(isGetcouponResponse);
      }
    }, Throwable::printStackTrace));
  }

  @Override public void getCartsNum() {
    mRxManager.add(mModel.getCartsNum().subscribe(cartsNumResultBean -> {
      mView.iniCartsNum(cartsNumResultBean);
    }, Throwable::printStackTrace));
  }

  @Override public void getPortalBean(SwipeRefreshLayout swipeRefreshLayout) {
    mRxManager.add(mModel.getPortalBean().subscribe(new ServiceResponse<PortalBean>() {
      @Override public void onNext(PortalBean portalBean) {
        if (null != portalBean) {
          mView.initSliderLayout(portalBean);

          mView.initCategory(portalBean);

          mView.initBrand(portalBean);

          mView.initPost(portalBean);
        }
      }

      @Override public void onCompleted() {
        if (swipeRefreshLayout != null) {
          swipeRefreshLayout.setRefreshing(false);
        }
      }

      @Override public void onError(Throwable e) {
        if (swipeRefreshLayout != null) {
          swipeRefreshLayout.setRefreshing(false);
        }
        JUtils.ToastLong("数据加载有误,请下拉刷新重试!");
      }
    }));
  }

  @Override public void getTodayList(int page, int page_size) {
    mRxManager.add(mModel.getTodayList(page, page_size).subscribe(productListBean -> {

    }, Throwable::printStackTrace));
  }

  @Override public void getUsercoupons(String template_id) {
    mRxManager.add(mModel.getUsercoupons(template_id).subscribe(responseBody -> {
      if (null != responseBody) {
        mView.clickGetCounpon(responseBody);
      }
    }, Throwable::printStackTrace));
  }

  @Override public long calcLefttowTime(long crtTime) {
    long left = 0;
    Date now = new Date();
    try {
      if (crtTime * 1000 - now.getTime() > 0) {
        left = crtTime * 1000 - now.getTime();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return left;
  }

  @Override public void onStart() {

  }
}
